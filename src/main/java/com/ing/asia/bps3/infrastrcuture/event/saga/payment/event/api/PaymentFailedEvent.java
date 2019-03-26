package com.ing.asia.bps3.infrastrcuture.event.saga.payment.event.api;

import java.math.BigDecimal;

public class PaymentFailedEvent extends BasePaymentEvent {

    public PaymentFailedEvent() {
    }

    public PaymentFailedEvent(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}


