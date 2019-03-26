package com.ing.asia.bps3.infrastrcuture.axon.saga.payment.event.api;

import java.math.BigDecimal;

public class PaymentSuccessfulEvent extends BasePaymentEvent {

    public PaymentSuccessfulEvent() {
    }

    public PaymentSuccessfulEvent(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}