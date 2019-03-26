package com.ing.asia.bps3.core.axon.saga.payment.command.api;

import java.math.BigDecimal;

public class ReversePaymentCommand extends BasePaymentCommand {

    public ReversePaymentCommand() {
    }

    public ReversePaymentCommand(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}
