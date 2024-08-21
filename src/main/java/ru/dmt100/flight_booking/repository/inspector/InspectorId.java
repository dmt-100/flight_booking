package ru.dmt100.flight_booking.repository.inspector;

import ru.dmt100.flight_booking.enums.TableType;

import java.sql.Connection;

public interface InspectorId<T> {
    boolean inspect(Connection con, TableType tableType, T key);
}
