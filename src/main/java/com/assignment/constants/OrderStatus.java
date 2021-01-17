package com.assignment.constants;

public enum OrderStatus {
    CREATED("created"),
    PAID("paid"),
    FAILED("failed"),
    ATTEMPTED("attempted");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
