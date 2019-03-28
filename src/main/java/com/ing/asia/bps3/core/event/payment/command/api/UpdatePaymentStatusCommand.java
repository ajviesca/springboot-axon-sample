package com.ing.asia.bps3.core.event.payment.command.api;

import com.ing.asia.bps3.core.domain.payment.Payment;

import java.math.BigDecimal;

public class UpdatePaymentStatusCommand extends BasePaymentCommand {

    private Payment.Status paymentStatus;

    public UpdatePaymentStatusCommand() {
    }

    public UpdatePaymentStatusCommand(Long paymentId, Long accountId, Long billerId, BigDecimal amount, Payment.Status paymentStatus) {
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
