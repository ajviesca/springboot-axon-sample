package com.ing.asia.bps3.infrastrcuture.event.saga.payment.event;

import java.math.BigDecimal;

public class PaymentSuccessfulEvent extends BasePaymentEvent {

    public PaymentSuccessfulEvent() {
    }

    public PaymentSuccessfulEvent(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}