package com.ing.asia.bps3.core.domain.payment;

import com.ing.asia.bps3.core.domain.biller.Biller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    private Long id;
    private BigDecimal amount;
    private Biller biller;
    private LocalDateTime postDate;
    private PaymentStatus status = PaymentStatus.PLACED;
    private Long paidByAccountId;

    public Payment() {

    }


    public Payment(BigDecimal amount, Biller biller, LocalDateTime postDate, PaymentStatus status, Long paidByAccountId) {
        this.amount = amount;
        this.biller = biller;
        this.postDate = postDate;
        this.status = status;
        this.paidByAccountId = paidByAccountId;
    }

    public Payment(Long id, BigDecimal amount, Biller biller, LocalDateTime postDate, PaymentStatus status, Long paidByAccountId) {
        this.id = id;
        this.amount = amount;
        this.biller = biller;
        this.postDate = postDate;
        this.status = status;
        this.paidByAccountId = paidByAccountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Biller getBiller() {
        return biller;
    }

    public void setBiller(Biller biller) {
        this.biller = biller;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Long getPaidByAccountId() {
        return paidByAccountId;
    }

    public void setPaidByAccountId(Long paidByAccountId) {
        this.paidByAccountId = paidByAccountId;
    }
}
