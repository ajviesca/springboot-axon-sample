package com.ing.asia.bps3.infrastrcuture.event.saga.payment.command;

import java.math.BigDecimal;

public class PayBillCommand extends BasePaymentCommand {

    public PayBillCommand() {
    }

    public PayBillCommand(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        super(paymentId, accountId, billerId, amount);
    }
}
