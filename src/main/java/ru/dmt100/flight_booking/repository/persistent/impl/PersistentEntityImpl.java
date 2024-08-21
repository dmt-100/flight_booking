package ru.dmt100.flight_booking.repository.persistent.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import ru.dmt100.flight_booking.enums.TableType;
import ru.dmt100.flight_booking.repository.mapper.Persistent;
import ru.dmt100.flight_booking.repository.persistent.PersistentEntity;
import ru.dmt100.flight_booking.util.validator.Validator;

import java.sql.Connection;
import java.sql.SQLException;

//@Component("persistentEntity")
public abstract class PersistentEntityImpl<T> implements PersistentEntity<T> {
    private final Validator validator;
    private final Persistent<T> persistent;
    private final String query;

    public PersistentEntityImpl(Validator validator,
                                @Qualifier("persistent") Persistent<T> persistent,
                                String query) {
        this.persistent = persistent;
        this.validator = validator;
        this.query = query;
    }

    protected abstract TableType getTableType();

    @Override
    public void save(Connection con, T t) {
        validator.validate(t);
        try (var stmt = con.prepareStatement(query)) {
            persistent.insertToDb(stmt, t);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
