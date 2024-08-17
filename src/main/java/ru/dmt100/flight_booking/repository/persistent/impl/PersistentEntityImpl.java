package ru.dmt100.flight_booking.repository.persistent.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import ru.dmt100.flight_booking.repository.persistent.PersistentEntity;
import ru.dmt100.flight_booking.repository.mapper.Persistent;
import ru.dmt100.flight_booking.sql.provider.SqlQueryProvider;

import java.sql.Connection;
import java.sql.SQLException;

//@Component("persistentEntity")
public abstract class PersistentEntityImpl<T> implements PersistentEntity<T> {
    private final Persistent<T> persistent;
    private final SqlQueryProvider sqlQueryProvider;
    private final String queryType;

    public PersistentEntityImpl(@Qualifier("persistent")Persistent<T> persistent,
                                SqlQueryProvider sqlQueryProvider,
                                String queryType) {
        this.persistent = persistent;
        this.sqlQueryProvider = sqlQueryProvider;
        this.queryType = queryType;
    }

    @Override
    public void save(Connection con, T t) {
        try(var stmt = con.prepareStatement(sqlQueryProvider.getQuery(queryType))) {
            persistent.insertToDb(stmt, t);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
