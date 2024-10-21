package ru.dmt100.flight_booking.dao.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.crud.eraser.EraserEntity;
import ru.dmt100.flight_booking.crud.persistent.PersistentEntity;
import ru.dmt100.flight_booking.crud.reader.ReaderEntity;
import ru.dmt100.flight_booking.dao.Dao;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.util.ConnectionManager;
import ru.dmt100.flight_booking.util.KeyProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional(readOnly = true)
public abstract class DaoImpl<K, T, TDto extends KeyProvider<K>, TResponse> implements Dao<Long, Integer, K, T, TDto, TResponse> {
    private final ReaderEntity<K, T, TDto> readerEntity;
    private final PersistentEntity<TDto> persistentEntity;
    private final EraserEntity<K> eraserEntity;

    public DaoImpl(ReaderEntity<K, T, TDto> readerEntity,
                          PersistentEntity<TDto> persistentEntity,
                          EraserEntity<K> eraserEntity) {
        this.readerEntity = readerEntity;
        this.persistentEntity = persistentEntity;
        this.eraserEntity = eraserEntity;
    }

    @Override
    public Optional<TResponse> save(Long userId, TDto tDto) {
        K key = getKey(tDto);
        try (var con = ConnectionManager.open()) {
            persistentEntity.save(con, tDto);
            return find(userId, key);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<TResponse> find(Long userId, K key) {
        try (Connection con = ConnectionManager.open()) {
            return readerEntity.load(con, key)
                    .map(result -> (TResponse) result)
                    .or(() -> {
                        throw new NotFoundException("Entity with key " + key + " does not exist");
                    });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TDto> find(Long userId, OffsetDateTime startTime, OffsetDateTime endTime, Integer limit) {
        try (var con = ConnectionManager.open()) {
            return (List<TDto>) readerEntity.load(con, startTime, endTime, limit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<TResponse> update(Long userId, TDto tDto) {
        K key = getKey(tDto);
        try (var con = ConnectionManager.open()) {
            eraserEntity.delete(con, key);
            persistentEntity.save(con, tDto);
            return readerEntity.load(con, key).map(result -> (TResponse) result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long userId, K key) {
        try (var con = ConnectionManager.open()) {
            eraserEntity.delete(con, key);
            return !readerEntity.load(con, key).isPresent();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<K, Boolean> deleteList(Long userId, List<K> keys) {
        Map<K, Boolean> deletedEntities = new HashMap<>();
        for (K key : keys) {
            try (var con = ConnectionManager.open()) {
                deletedEntities.put(key, eraserEntity.delete(con, key));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return deletedEntities;
    }

    private K getKey(TDto t) {
        return t.getKey();
    }


}
