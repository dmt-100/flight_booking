package ru.dmt100.flight_booking.repository.eraser.impl;

import ru.dmt100.flight_booking.repository.eraser.EraserEntity;
import ru.dmt100.flight_booking.sql.provider.SqlQueryProvider;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class EraserEntityImpl<K> implements EraserEntity<K> {

    private final SqlQueryProvider sqlQueryProvider;
    private final String queryType;

    protected EraserEntityImpl(SqlQueryProvider sqlQueryProvider, String queryType) {
        this.sqlQueryProvider = sqlQueryProvider;
        this.queryType = queryType;
    }


    @Override
    public void delete(Connection con, K key) {
        try (var stmt = con.prepareStatement(queryType)) {
            stmt.setObject(1, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
