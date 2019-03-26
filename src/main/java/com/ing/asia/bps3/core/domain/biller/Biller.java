package com.ing.asia.bps3.core.domain.biller;

public class Biller {
    private Long id;
    private String billerName;

    public Biller() {
    }

    public Biller(Long id, String billerName) {
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
