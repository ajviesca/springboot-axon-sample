package com.ing.asia.bps3.core.event.payment.command.api;

import java.math.BigDecimal;

public class DebitSourceAccountCommand extends BasePaymentCommand {

    public DebitSourceAccountCommand() {
    }

    public DebitSourceAccountCommand(Long paymentId, Long accountId,
                                     Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}
