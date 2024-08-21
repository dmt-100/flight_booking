package ru.dmt100.flight_booking.util.validator;

public interface Validator<T> {
    T validate(T t);
}
