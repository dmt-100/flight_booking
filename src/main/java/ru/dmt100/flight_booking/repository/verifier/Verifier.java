package ru.dmt100.flight_booking.repository.verifier;

import java.sql.Connection;

public interface Verifier<K> {
    boolean isExist(Connection con, K key);
}
