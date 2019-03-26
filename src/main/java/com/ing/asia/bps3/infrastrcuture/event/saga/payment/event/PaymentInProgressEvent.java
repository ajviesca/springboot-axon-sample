package com.ing.asia.bps3.infrastrcuture.event.saga.payment.event;

import java.math.BigDecimal;

public class PaymentInProgressEvent extends BasePaymentEvent {

    public PaymentInProgressEvent() {
    }

    public PaymentInProgressEvent(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}