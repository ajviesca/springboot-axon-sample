package com.ing.asia.bps3.core.axon.saga.payment.command.api;

import java.math.BigDecimal;

public class PayBillCommand extends BasePaymentCommand {

    public PayBillCommand() {
    }

    public PayBillCommand(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}
