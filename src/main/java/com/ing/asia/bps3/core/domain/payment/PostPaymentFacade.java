package com.ing.asia.bps3.core.domain.payment;

import com.ing.asia.bps3.core.domain.account.AccountRepository;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import com.ing.asia.bps3.core.event.payment.command.api.*;
import com.ing.asia.bps3.core.event.payment.command.handler.executor.*;
import com.ing.asia.bps3.core.event.payment.event.api.BasePaymentEvent;
import com.ing.asia.bps3.core.event.payment.event.api.PaymentFailedEvent;
import com.ing.asia.bps3.core.event.payment.event.api.PaymentInProgressEvent;
import com.ing.asia.bps3.core.event.payment.event.api.SourceAccountInsufficientBalanceEvent;

import java.math.BigDecimal;

import static com.ing.asia.bps3.core.domain.payment.PaymentStatus.*;

public class PostPaymentFacade {

    private final BillerRepository billerRepository;
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    public PostPaymentFacade(BillerRepository billerRepository,
                             PaymentRepository paymentRepository,
                             AccountRepository accountRepository) {
        this.billerRepository = billerRepository;
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    public Payment postPayment(PostPaymentCommand command) {
        PaymentInProgressEvent paymentInProgressEvent = createInitialPaymentRecord(command);
        Long paymentId = paymentInProgressEvent.getPaymentId();
        Long accountId = command.getAccountId();
        Long billerId = command.getBillerId();
        BigDecimal paymentAmount = command.getPaymentAmount();
        BasePaymentEvent debitSourceAccountResultEvent = debitSourceAccount(
                new DebitSourceAccountCommand(paymentId, accountId, billerId, paymentAmount));

        if (isInsufficientBalance(debitSourceAccountResultEvent)) {
            return markPaymentAs(FAILED_INSUFFICIENT_BALANCE, paymentId, accountId, billerId, paymentAmount);
        }

        BasePaymentEvent sendPaymentToBillerReultEvent = sendPaymentToBiller(paymentId, accountId, billerId, paymentAmount);

        if (isPaymentUnsuccesful(sendPaymentToBillerReultEvent)) {
            new ReversePaymentExecutor(accountRepository,
                    new ReversePaymentCommand(paymentId, accountId, billerId, paymentAmount))
                    .execute();
            return markPaymentAs(FAILED_AND_REVERSED, paymentId, accountId, billerId, paymentAmount);
        }

        return markPaymentAs(COMPLETED, paymentId, accountId, billerId, paymentAmount);
    }

    private boolean isPaymentUnsuccesful(BasePaymentEvent sendPaymentToBillerReultEvent) {
        return sendPaymentToBillerReultEvent instanceof PaymentFailedEvent;
    }

    private Payment getPaymentRecord(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    private BasePaymentEvent sendPaymentToBiller(Long paymentId, Long accountId, Long billerId, BigDecimal paymentAmount) {
        return new SendPaymentToBillerExecutor(
                new SendPaymentToBillerCommand(paymentId, accountId, billerId, paymentAmount))
                .execute()
                .getResultEvent();
    }

    private Payment markPaymentAs(PaymentStatus paymentStatus, Long paymentId, Long accountId, Long billerId, BigDecimal paymentAmount) {
        UpdatePaymentStatusCommand updatePaymentStatusCommand = new UpdatePaymentStatusCommand(paymentId,
                accountId, billerId, paymentAmount, paymentStatus);
        new UpdatePaymentStatusExecutor(paymentRepository, updatePaymentStatusCommand)
                .execute();
        return getPaymentRecord(paymentId);
    }

    private boolean isInsufficientBalance(BasePaymentEvent debitSourceAccountResultEvent) {
        return debitSourceAccountResultEvent instanceof SourceAccountInsufficientBalanceEvent;
    }

    private BasePaymentEvent debitSourceAccount(DebitSourceAccountCommand command) {
        return new DebitSourceAccountExecutor(accountRepository, command)
                .execute()
                .getResultEvent();
    }

    private PaymentInProgressEvent createInitialPaymentRecord(PostPaymentCommand command) {
        return new PostPaymentExecutor(billerRepository, paymentRepository, command)
                .execute()
                .getResultEvent();
    }
}
