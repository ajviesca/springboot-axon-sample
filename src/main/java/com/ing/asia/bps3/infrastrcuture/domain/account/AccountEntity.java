package com.ing.asia.bps3.infrastrcuture.domain.account;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "ACCOUNT")
public class AccountEntity {
    @Id
    private Long id;
    private String name;
    private BigDecimal balance;

    public AccountEntity() {
    }

    public AccountEntity(Long id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
