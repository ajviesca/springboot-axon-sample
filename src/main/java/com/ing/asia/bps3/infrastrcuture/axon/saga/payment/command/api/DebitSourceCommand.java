package com.ing.asia.bps3.infrastrcuture.axon.saga.payment.command.api;

import java.math.BigDecimal;

public class DebitSourceCommand extends BasePaymentCommand {

    public DebitSourceCommand() {
    }

    public DebitSourceCommand(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}
