package com.test360.business.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Ordering implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    private Date orderTime;

    private String menuType;

    private String location;

    private BigDecimal price;

    private Byte isTaken;

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

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Byte getIsTaken() {
        return isTaken;
    }

    public void setIsTaken(Byte isTaken) {
        this.isTaken = isTaken;
    }

    /**
     * java.lang.Object#toString()
     */
    public String toString() {
        return "Ordering [ id=" + id +",   userId=" + userId +",   orderTime=" + orderTime +",   menuType=" + menuType +",   location=" + location +",   price=" + price +",   isTaken=" + isTaken + " ] " ;
    }
}