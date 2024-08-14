package ru.dmt100.flight_booking.fetcher;

import lombok.AllArgsConstructor;
import ru.dmt100.flight_booking.dao.Fetcher;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.mapper.EntityMapper;
import ru.dmt100.flight_booking.sql.SqlQueryProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class EntityFetcher<T, K> implements Fetcher<T, K> {
    private final EntityMapper<T> mapper;
    private final SqlQueryProvider sqlQueryProvider;
    private final String queryType;

    @Override
    public Optional<T> fetch(Connection con, K key) {
        try (var stmt = con.prepareStatement(sqlQueryProvider.getQuery(queryType))) {
            stmt.setObject(1, key);
            try(var rs = stmt.executeQuery()) {
                if (!rs.next()) {

                    throw new NotFoundException("Entity with key " + key + "does not exist");

                } else {
                    T entity = mapper.map(con, rs);
                    return Optional.of(entity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
