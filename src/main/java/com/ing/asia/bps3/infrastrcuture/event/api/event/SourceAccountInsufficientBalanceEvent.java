package com.ing.asia.bps3.infrastrcuture.event.api.event;

import java.math.BigDecimal;

public class SourceAccountInsufficientBalanceEvent extends BasePaymentEvent{

    public SourceAccountInsufficientBalanceEvent() {
    }

    public SourceAccountInsufficientBalanceEvent(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}
