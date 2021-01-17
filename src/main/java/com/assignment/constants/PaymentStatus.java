package com.assignment.constants;

public enum PaymentStatus {
    CAPTURED("captured"),
    REFUND("refund"),
    AUTHORIZED("authorized"),
    FAILED("failed");

    private String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
