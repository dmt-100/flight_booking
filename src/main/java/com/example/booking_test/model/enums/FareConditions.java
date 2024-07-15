package com.example.booking_test.model.enums;


public enum FareConditions {
    ECONOMY("Economy"),
    COMFORT("Comfort"),
    BUSINESS("Business");

    private final String value;

    FareConditions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
