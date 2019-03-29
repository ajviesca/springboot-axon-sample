package com.ing.asia.bps3.core.event.payment.command.api;

import com.ing.asia.bps3.core.domain.payment.PaymentStatus;

import java.math.BigDecimal;

public class UpdatePaymentStatusCommand extends BasePaymentCommand {

    private PaymentStatus paymentStatus;

    public UpdatePaymentStatusCommand() {
    }

    public UpdatePaymentStatusCommand(Long paymentId, Long accountId, Long billerId, BigDecimal amount, PaymentStatus paymentStatus) {
        super(paymentId, accountId, billerId, amount);
        this.paymentStatus = paymentStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
