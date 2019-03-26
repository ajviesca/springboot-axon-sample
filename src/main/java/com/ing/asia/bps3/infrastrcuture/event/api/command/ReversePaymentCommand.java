package com.ing.asia.bps3.infrastrcuture.event.api.command;

import java.math.BigDecimal;

public class ReversePaymentCommand extends BasePaymentCommand{

    public ReversePaymentCommand() {
    }

    public ReversePaymentCommand(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}
