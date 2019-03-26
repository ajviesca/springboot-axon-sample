package com.ing.asia.bps3.infrastrcuture.domain.biller;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BILLER")
public class BillerEntity {
    @Id
    private Long id;

    private String billerName;

    public BillerEntity() {
    }

    public BillerEntity(Long id, String billerName) {
        this.id = id;
        this.billerName = billerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }
}
