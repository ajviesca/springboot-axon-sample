package com.ing.asia.bps3.core.event.payment.event.api;

import java.math.BigDecimal;

public class BasePaymentEvent {

    private Long paymentId;
    private Long accountId;
    private Long billerId;
    private BigDecimal paymentAmount;

    public BasePaymentEvent() {
    }

    public BasePaymentEvent(Long paymentId, Long accountId, Long billerId, BigDecimal paymentAmount) {
        this.paymentId = paymentId;
        this.accountId = accountId;
        this.billerId = billerId;
        this.paymentAmount = paymentAmount;
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

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
