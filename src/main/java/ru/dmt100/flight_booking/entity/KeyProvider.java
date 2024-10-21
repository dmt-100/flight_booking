package ru.dmt100.flight_booking.entity;

public interface KeyProvider<K, TDto> {

    K getKey(TDto tDto);
}
