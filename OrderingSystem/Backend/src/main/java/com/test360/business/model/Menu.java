package com.test360.business.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Date menuDate;

    private String menuType;

    private String menu;

    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(Date menuDate) {
        this.menuDate = menuDate;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * java.lang.Object#toString()
     */
    public String toString() {
        return "Menu [ id=" + id +",   menuDate=" + menuDate +",   menuType=" + menuType +",   menu=" + menu +",   price=" + price + " ] " ;
    }
}