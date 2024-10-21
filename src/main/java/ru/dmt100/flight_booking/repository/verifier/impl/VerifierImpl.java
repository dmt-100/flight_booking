package ru.dmt100.flight_booking.repository.verifier.impl;

import ru.dmt100.flight_booking.repository.verifier.Verifier;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class VerifierImpl<K> implements Verifier<K> {

    private final String query;

    protected VerifierImpl(String query) {
        this.query = query;
    }

    @Override
    public boolean isExist(Connection con, K key) {
        try (var stmt = con.prepareStatement(query)) {
            stmt.setObject(1, key);
            try (var rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
