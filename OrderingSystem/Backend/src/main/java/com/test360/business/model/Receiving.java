package com.test360.business.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Receiving implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    private Date getTime;

    private String menuType;

    private String location;

    private BigDecimal price;

    private Byte isError;

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

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
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

    public Byte getIsError() {
        return isError;
    }

    public void setIsError(Byte isError) {
        this.isError = isError;
    }

    /**
     * java.lang.Object#toString()
     */
    public String toString() {
        return "Receiving [ id=" + id +",   userId=" + userId +",   getTime=" + getTime +",   menuType=" + menuType +",   location=" + location +",   price=" + price +",   isError=" + isError + " ] " ;
    }
}