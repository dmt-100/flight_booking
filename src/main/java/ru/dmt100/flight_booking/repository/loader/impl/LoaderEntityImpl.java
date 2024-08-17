package ru.dmt100.flight_booking.repository.loader.impl;

import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.repository.loader.LoaderEntity;
import ru.dmt100.flight_booking.repository.mapper.Loader;
import ru.dmt100.flight_booking.sql.provider.SqlQueryProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class LoaderEntityImpl<T, K> implements LoaderEntity<T, K> {
    private final Loader<T> loader;
    private final SqlQueryProvider sqlQueryProvider;
    private final String queryType;

    public LoaderEntityImpl(Loader<T> loader,
                            SqlQueryProvider sqlQueryProvider,
                            String queryType) {
        this.loader = loader;
        this.sqlQueryProvider = sqlQueryProvider;
        this.queryType = queryType;
    }

    @Override
    public Optional<T> load(Connection con, K key) {
        String query = sqlQueryProvider.getQuery(queryType);
        try (var stmt = con.prepareStatement(query)) {
            stmt.setObject(1, key);
            try (var rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new NotFoundException("Entity with key " + key + " does not exist");
                } else {
                    T entity = loader.map(con, rs).orElseThrow(() ->
                            new NotFoundException("Entity mapping failed for key " + key));
                    return Optional.of(entity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> loadAll(Connection con, Integer limit) {
        List<T> list = new ArrayList<>();
        try (var stmt = con.prepareStatement(sqlQueryProvider.getQuery(queryType))) {
            try (var rs = stmt.executeQuery()) {

                while (list.size() <= (Integer) limit) {
                    if (!rs.next()) {
                        throw new NotFoundException("No one entity does not exist");
                    } else {
                        Optional<T> e = loader.map(con, rs);
                        e.ifPresent(list::add);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
