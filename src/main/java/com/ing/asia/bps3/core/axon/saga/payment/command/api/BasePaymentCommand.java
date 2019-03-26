package com.ing.asia.bps3.core.axon.saga.payment.command.api;

import java.math.BigDecimal;

public class BasePaymentCommand {

    private Long paymentId;
    private Long accountId;
    private Long billerId;
    private BigDecimal amount;

    public BasePaymentCommand() {
    }

    public BasePaymentCommand(Long paymentId, Long accountId, Long billerId, BigDecimal amount) {
        this.paymentId = paymentId;
        this.accountId = accountId;
        this.billerId = billerId;
        this.amount = amount;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getBillerId() {
        return billerId;
    }

    public void setBillerId(Long billerId) {
        this.billerId = billerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
