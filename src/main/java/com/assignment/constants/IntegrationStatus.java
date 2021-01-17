package com.assignment.constants;

public enum IntegrationStatus {
    INITIATED("initiated"),
    SUCCESS("success"),
    FAILURE("failure");

    private String integrationStatus;


    IntegrationStatus(String integrationStatus) {
        this.integrationStatus = integrationStatus;
    }

    public String getIntegrationStatus() {
        return integrationStatus;
    }
}
