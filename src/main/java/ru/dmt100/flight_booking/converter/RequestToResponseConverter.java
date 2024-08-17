package ru.dmt100.flight_booking.converter;

public interface RequestToResponseConverter<T1, T2> {
    T2 convertToResponse(T1 t1);
    T1 convertToRequest(T2 t2);
}
