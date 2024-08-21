package ru.dmt100.flight_booking.util.converter;

public interface ConverterToRequest<T1, T2> {
    public T1 convertToRequest(T2 t2);
}
