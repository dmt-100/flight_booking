package ru.dmt100.flight_booking.repository.reader.impl;

import ru.dmt100.flight_booking.enums.TableType;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.repository.mapper.Loader;
import ru.dmt100.flight_booking.repository.reader.ReaderEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ReaderEntityImpl<K, T> implements ReaderEntity<K, T> {
    private final Loader<T> loader;
    private final String findQuery;
    private final String findAllQuery;
    public ReaderEntityImpl(Loader<T> loader,
                            String findQuery,
                            String findAllQuery) {
        this.loader = loader;
        this.findQuery = findQuery;
        this.findAllQuery = findAllQuery;
    }

    protected abstract TableType getTableType();

    @Override
    public Optional<T> load(Connection con, K key) {
        try (var stmt = con.prepareStatement(findQuery)) {

            stmt.setObject(1, key);
            try (var rs = stmt.executeQuery()) {

                if (!rs.next()) {
                    throw new NotFoundException("Entity with key " + key + " does not exist");
                } else {
                    T entity = loader.getOptional(con, rs).orElseThrow(() ->
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
        try (var stmt = con.prepareStatement(findAllQuery)) {
            try (var rs = stmt.executeQuery()) {

                while (list.size() <= limit) {
                    if (!rs.next()) {
                        throw new NotFoundException("No one entity does not exist");
                    } else {
                        T e = loader.get(con, rs);
                        list.add(e);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
