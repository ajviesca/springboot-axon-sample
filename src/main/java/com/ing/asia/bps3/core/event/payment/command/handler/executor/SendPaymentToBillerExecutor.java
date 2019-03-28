package com.ing.asia.bps3.core.event.payment.command.handler.executor;

import com.ing.asia.bps3.core.domain.biller.Biller;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import com.ing.asia.bps3.core.event.payment.command.api.SendPaymentToBillerCommand;
import com.ing.asia.bps3.core.event.payment.event.api.BasePaymentEvent;
import com.ing.asia.bps3.core.event.payment.event.api.PaymentFailedEvent;
import com.ing.asia.bps3.core.event.payment.event.api.PaymentSuccessfulEvent;

import java.math.BigDecimal;

public class SendPaymentToBillerExecutor {

    private BillerRepository billerRepository;
    private BigDecimal paymentAmount;
    private Long paymentId;
    private Long accountId;
    private Long billerId;

    private Biller biller;

    public SendPaymentToBillerExecutor(BillerRepository billerRepository, SendPaymentToBillerCommand command) {
        this.billerRepository = billerRepository;
        this.paymentAmount = command.getPaymentAmount();
        this.paymentId = command.getPaymentId();
        this.accountId = command.getAccountId();
        this.billerId = command.getBillerId();
    }

    public BasePaymentEvent execute() {
        getBillerRecord();

        if (isPositivePaymentAmount()) {
            return createPaymentSuccessfulEvent();
        } else {
            return createPaymentFailedEvent();
        }
    }

    private void getBillerRecord() {
        biller = billerRepository.findById(billerId);
    }

    private boolean isPositivePaymentAmount() {
        return paymentAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    private PaymentSuccessfulEvent createPaymentSuccessfulEvent() {
        return new PaymentSuccessfulEvent(paymentId, accountId, billerId,
                paymentAmount);
    }

    private PaymentFailedEvent createPaymentFailedEvent() {
        return new PaymentFailedEvent(paymentId, accountId, billerId,
                paymentAmount);
    }

}
