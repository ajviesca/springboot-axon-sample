package com.ing.asia.bps3.core.event.payment.command.handler.executor;

import com.ing.asia.bps3.core.domain.biller.Biller;
import com.ing.asia.bps3.core.domain.biller.BillerRepository;
import com.ing.asia.bps3.core.domain.payment.Payment;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.core.domain.payment.PaymentStatus;
import com.ing.asia.bps3.core.event.payment.command.api.PostPaymentCommand;
import com.ing.asia.bps3.core.event.payment.event.api.PaymentInProgressEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PostPaymentExecutor {

    private BillerRepository billerRepository;
    private PaymentRepository paymentRepository;
    private BigDecimal paymentAmount;
    private Long accountId;
    private Long billerId;
    private Payment payment;
    private Biller biller;
    private PaymentInProgressEvent resultEvent;

    public PostPaymentExecutor(BillerRepository billerRepository, PaymentRepository paymentRepository, PostPaymentCommand command) {
        this.billerRepository = billerRepository;
        this.paymentRepository = paymentRepository;
        this.paymentAmount = command.getPaymentAmount();
        this.accountId = command.getAccountId();
        this.billerId = command.getBillerId();
    }

    public PostPaymentExecutor execute() {
        getBillerRecord();
        createPaymentRecord();
        savePaymentRecord();
        createPaymentInProgressevent();
        return this;
    }

    public PaymentInProgressEvent getResultEvent() {
        return resultEvent;
    }

    private void getBillerRecord() {
        biller = billerRepository.findById(billerId);
    }

    private void createPaymentRecord() {
        payment = new Payment(paymentAmount, biller, LocalDateTime.now(), PaymentStatus.PLACED, accountId);
    }

    private void savePaymentRecord() {
        payment = paymentRepository.save(payment);
    }

    private void createPaymentInProgressevent() {
        resultEvent = new PaymentInProgressEvent(payment.getId(), payment.getPaidByAccountId(),
                biller.getId(), payment.getAmount());
    }
}
