package com.ing.asia.bps3.infrastrcuture.domain.payment;

import java.math.BigDecimal;

public class PostPaymentSave {
    private Long billerId;
    private BigDecimal amount;
    private Long accountId;


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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
