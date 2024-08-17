package ru.dmt100.flight_booking.repository.eraser;

import java.sql.Connection;

public interface EraserEntity<K> {
    void delete(Connection con, K key);
}
