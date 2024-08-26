package com.sparta.zlzonedelivery.user;

public enum UserRole {

    CUSTOMER("CUSTOMER"),
    OWNER("OWNER"),
    MANAGER("MANAGER"),
    MASTER("MASTER");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
