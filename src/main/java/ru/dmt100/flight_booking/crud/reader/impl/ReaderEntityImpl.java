package ru.dmt100.flight_booking.crud.reader.impl;

import org.springframework.stereotype.Repository;
import ru.dmt100.flight_booking.crud.reader.ReaderEntity;
import ru.dmt100.flight_booking.enums.TableType;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.repository.mapper.Loader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ReaderEntityImpl<K, T, TDto> implements ReaderEntity<K, T, TDto> {
    private final Loader<T, TDto> loader;
    private final String findQuery;
    private final String findAllQueryByTimeRange;

    public ReaderEntityImpl(Loader<T, TDto> loader,
                            String findQuery,
                            String findAllQueryByTimeRange) {
        this.loader = loader;
        this.findQuery = findQuery;
        this.findAllQueryByTimeRange = findAllQueryByTimeRange;
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
    public List<TDto> load(Connection con,
                        OffsetDateTime startTime,
                        OffsetDateTime endTime,
                        Integer limit) {
        List<TDto> list = new ArrayList<>();
        try (var stmt = con.prepareStatement(findAllQueryByTimeRange)) {
            stmt.setTimestamp(1, Timestamp.from(startTime.toInstant()));
            stmt.setTimestamp(2, Timestamp.from(endTime.toInstant()));
            stmt.setInt(3, limit);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TDto e = (TDto) loader.get(con, rs);
                    list.add(e);
                }
                if (list.isEmpty()) {
                    throw new NotFoundException("No entities found within the specified time range");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
