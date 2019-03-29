package com.ing.asia.bps3.infrastrcuture.domain.payment;

import com.ing.asia.bps3.core.domain.payment.PaymentStatus;
import com.ing.asia.bps3.infrastrcuture.domain.biller.BillerEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT")
public class PaymentEntity {
    @Id
    private Long id;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(columnDefinition = "biller_id", referencedColumnName = "id")
    private BillerEntity biller;

    private LocalDateTime postDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Long paidByAccountId;

    public PaymentEntity() {
    }

    public PaymentEntity(Long id, BigDecimal amount, BillerEntity biller, LocalDateTime postDate, PaymentStatus status, Long paidByAccountId) {
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

    public BillerEntity getBiller() {
        return biller;
    }

    public void setBiller(BillerEntity biller) {
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
