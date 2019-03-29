package com.ing.asia.bps3.core.event.payment.event.api;

import java.math.BigDecimal;

public class SourceAccountDebitedEvent extends BasePaymentEvent {

    public SourceAccountDebitedEvent() {
    }

    public SourceAccountDebitedEvent(Long paymentId, Long accountId, Long billerId,
                                     BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}