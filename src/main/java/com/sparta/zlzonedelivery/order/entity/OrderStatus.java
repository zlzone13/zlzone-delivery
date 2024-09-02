package com.sparta.zlzonedelivery.order.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    DELIVERY("DELIVERY"),
    DELIVERED("DELIVERED"),
    CANCELED("CANCELED");

    private final String orderStatus;

    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
