package com.ing.asia.bps3.core.axon.saga.payment.event.api;

import java.math.BigDecimal;

public class SourceAccountInsufficientBalanceEvent extends BasePaymentEvent {

    public SourceAccountInsufficientBalanceEvent() {
    }

    public SourceAccountInsufficientBalanceEvent(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}
