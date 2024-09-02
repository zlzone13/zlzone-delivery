package com.sparta.zlzonedelivery.order.entity;

import lombok.Getter;

@Getter
public enum OrderType {

    ONLINE("ONLINE"),
    OFFLINE("OFFLINE");

    private final String orderType;

    OrderType(String orderType) {
        this.orderType = orderType;
    }

}
