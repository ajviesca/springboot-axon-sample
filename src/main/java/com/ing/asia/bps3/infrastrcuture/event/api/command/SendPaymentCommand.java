package com.ing.asia.bps3.infrastrcuture.event.api.command;

import java.math.BigDecimal;

public class SendPaymentCommand extends BasePaymentCommand {

    public SendPaymentCommand() {
    }

    public SendPaymentCommand(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}
