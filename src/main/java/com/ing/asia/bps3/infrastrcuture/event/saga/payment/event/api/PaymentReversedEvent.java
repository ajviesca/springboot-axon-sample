package com.ing.asia.bps3.infrastrcuture.event.saga.payment.event.api;

import java.math.BigDecimal;

public class PaymentReversedEvent extends BasePaymentEvent {

    public PaymentReversedEvent() {
    }

    public PaymentReversedEvent(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}
