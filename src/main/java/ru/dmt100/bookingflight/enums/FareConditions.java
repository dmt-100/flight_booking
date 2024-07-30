package ru.dmt100.bookingflight.enums;

public enum FareConditions {
    ECONOMY("Economy"),
    COMFORT("Comfort"),
    BUSINESS("Business");

    private final String condition;

    FareConditions(String condition) {
        this.condition = condition;
    }

    public String getValue() {
        return condition;
    }
}
