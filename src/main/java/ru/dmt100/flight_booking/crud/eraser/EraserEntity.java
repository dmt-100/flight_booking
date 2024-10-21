package ru.dmt100.flight_booking.crud.eraser;

import java.sql.Connection;

public interface EraserEntity<K> {
    boolean delete(Connection con, K key);
}
