package com.ing.asia.bps3.core.entity.payment;

import com.ing.asia.bps3.core.entity.biller.Biller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    public enum Status {
        PLACED, COMPLETED, FAILED_AND_REVERSED, FAILED_INSUFFICIENT_BALANCE
    }

    private Long id;
    private BigDecimal amount;
    private Biller biller;
    private LocalDateTime postDate;
    private Status status = Status.PLACED;
    private Long paidByAccountId;

    public Payment() {

    }

    public Payment(Long id, BigDecimal amount, Biller biller, LocalDateTime postDate) {
        this.id = id;
        this.amount = amount;
        this.biller = biller;
        this.postDate = postDate;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getPaidByAccountId() {
        return paidByAccountId;
    }

    public void setPaidByAccountId(Long paidByAccountId) {
        this.paidByAccountId = paidByAccountId;
    }
}
