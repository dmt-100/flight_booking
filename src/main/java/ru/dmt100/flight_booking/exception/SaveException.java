package ru.dmt100.flight_booking.exception;

public class SaveException extends RuntimeException {
    public SaveException(String message) {
        super(message);
    }
}
