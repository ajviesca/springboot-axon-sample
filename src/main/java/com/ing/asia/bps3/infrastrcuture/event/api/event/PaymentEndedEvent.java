package com.ing.asia.bps3.infrastrcuture.event.api.event;

import java.math.BigDecimal;

public class PaymentEndedEvent extends BasePaymentEvent {

    public PaymentEndedEvent() {
    }

    public PaymentEndedEvent(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}
