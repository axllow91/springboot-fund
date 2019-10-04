package com.mrn.demohelloworld.dtos;

import com.mrn.demohelloworld.entities.Order;

import java.util.List;

public class UserMnDto {
    private Long userId;
    private String username;
    private String firstName;
    private List<Order> orders;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
