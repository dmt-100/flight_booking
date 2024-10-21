package ru.dmt100.flight_booking.crud.eraser.impl;

import ru.dmt100.flight_booking.crud.eraser.EraserEntity;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class EraserEntityImpl<K> implements EraserEntity<K> {
    private final String query;

    protected EraserEntityImpl(String query) {
        this.query = query;
    }

    @Override
    public boolean delete(Connection con, K key) {
        try (var stmtDelete = con.prepareStatement(query)) {
            stmtDelete.setObject(1, key);
            int deleted = stmtDelete.executeUpdate();

            if (deleted == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
