package ru.dmt100.flight_booking.airport.enums;

public enum Status {
    Arrived,
    Cancelled,
    Delayed,
    Departed,
    OnTime("On Time"),
    Scheduled;

    private final String dbValue;

    Status() {
        this.dbValue = this.name();
    }

    Status(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static Status fromDbValue(String dbValue) {
        for (Status status : values()) {
            if (status.dbValue.equals(dbValue)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant for db value: " + dbValue);
    }
}
