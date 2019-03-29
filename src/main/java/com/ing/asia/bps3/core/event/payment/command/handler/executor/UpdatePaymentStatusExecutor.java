package com.ing.asia.bps3.core.event.payment.command.handler.executor;

import com.ing.asia.bps3.core.domain.payment.Payment;
import com.ing.asia.bps3.core.domain.payment.PaymentRepository;
import com.ing.asia.bps3.core.domain.payment.PaymentStatus;
import com.ing.asia.bps3.core.event.payment.command.api.UpdatePaymentStatusCommand;
import com.ing.asia.bps3.core.event.payment.event.api.PaymentEndedEvent;

import java.math.BigDecimal;

public class UpdatePaymentStatusExecutor {

    private PaymentRepository paymentRepository;
    private Long paymentId;
    private PaymentStatus paymentStatus;
    private Long accountId;
    private Long billerId;
    private BigDecimal paymentAmount;
    private Payment payment;

    public UpdatePaymentStatusExecutor(PaymentRepository paymentRepository, UpdatePaymentStatusCommand command) {
        this.paymentRepository = paymentRepository;
        this.paymentId = command.getPaymentId();
        this.paymentStatus = command.getPaymentStatus();
        this.accountId = command.getAccountId();
        this.billerId = command.getBillerId();
        this.paymentAmount = command.getPaymentAmount();
    }

    public PaymentEndedEvent execute() {
        getPaymentRecord();
        updatePaymentStatus();
        updatePaymentRecord();
        return createPaymentEndedEvent();
    }

    private void getPaymentRecord() {
        payment = paymentRepository.findById(paymentId);
    }

    private void updatePaymentStatus() {
        payment.setStatus(paymentStatus);
    }


    private void updatePaymentRecord() {
        payment = paymentRepository.update(payment);
    }

    private PaymentEndedEvent createPaymentEndedEvent() {
        return new PaymentEndedEvent(paymentId, accountId, billerId,
                paymentAmount);
    }

}
