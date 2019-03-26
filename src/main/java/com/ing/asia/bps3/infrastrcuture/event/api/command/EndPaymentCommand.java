package com.ing.asia.bps3.infrastrcuture.event.api.command;

import com.ing.asia.bps3.core.entity.payment.Payment;

import java.math.BigDecimal;

public class EndPaymentCommand extends BasePaymentCommand {

    private Payment.Status paymentStatus;

    public EndPaymentCommand() {
    }

    public EndPaymentCommand(Long paymentId, Long accountId, Long billerId, BigDecimal amount, Payment.Status paymentStatus) {
        super(paymentId, accountId, billerId, amount);
        this.paymentStatus = paymentStatus;
    }

    public Payment.Status getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Payment.Status paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
