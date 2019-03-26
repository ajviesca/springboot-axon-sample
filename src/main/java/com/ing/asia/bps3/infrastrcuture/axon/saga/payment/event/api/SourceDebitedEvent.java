package com.ing.asia.bps3.infrastrcuture.axon.saga.payment.event.api;

import java.math.BigDecimal;

public class SourceDebitedEvent extends BasePaymentEvent {

    public SourceDebitedEvent() {
    }

    public SourceDebitedEvent(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}