package com.ing.asia.bps3.core.event.payment.command.api;

import java.math.BigDecimal;

public class SendPaymentToBillerCommand extends BasePaymentCommand {

    public SendPaymentToBillerCommand() {
    }

    public SendPaymentToBillerCommand(Long paymentId, Long accountId, Long billerId,
                                      BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}
