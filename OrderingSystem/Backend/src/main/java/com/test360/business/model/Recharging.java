package com.test360.business.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Recharging implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    private Date rechargeTime;

    private BigDecimal money;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * java.lang.Object#toString()
     */
    public String toString() {
        return "Recharging [ id=" + id +",   userId=" + userId +",   rechargeTime=" + rechargeTime +",   money=" + money + " ] " ;
    }
}