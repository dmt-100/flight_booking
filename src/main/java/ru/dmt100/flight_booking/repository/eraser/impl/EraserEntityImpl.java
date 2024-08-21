package ru.dmt100.flight_booking.repository.eraser.impl;

import ru.dmt100.flight_booking.repository.eraser.EraserEntity;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class EraserEntityImpl<K> implements EraserEntity<K> {
    private final String query;

    protected EraserEntityImpl(String query) {
        this.query = query;
    }

    @Override
    public void delete(Connection con, K key) {
        try (var stmtDelete = con.prepareStatement(query)) {
            stmtDelete.setObject(1, key);
            int deleted = stmtDelete.executeUpdate();

            if (deleted == 0) {
                throw new RuntimeException("Object not found or not deleted");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
