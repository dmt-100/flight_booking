//package ru.dmt100.flight_booking.dao.impl;
//
//import ru.dmt100.flight_booking.dao.Dao;
//import ru.dmt100.flight_booking.repository.eraser.EraserEntity;
//import ru.dmt100.flight_booking.repository.persistent.PersistentEntity;
//import ru.dmt100.flight_booking.util.ConnectionManager;
//import ru.dmt100.flight_booking.util.validator.Validator;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Optional;
//
//public abstract class DaoImpl<
//        K,
//        T,
//        T1 extends PersistentEntity,
//        T2,
//        T3,
//        T4 extends EraserEntity,
//        T5,
//        T6 extends Validator> implements Dao {
//    T1 persistent;
//    T2 reader;
//    T3 readerAll;
//    T4 eraser;
//    T5 updater;
//    T6 validator;
//
//    Optional<T> save(Long userId, T t)  {
//        validator.validate(t);
//        String query =
//
//    }
//
////    public void save(Connection con, T t) {
////        validator.validate(t);
////        String query = queryType.getQuery();
////        try (var stmt = con.prepareStatement(query)) {
////            persistent.insertToDb(stmt, t);
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////    }
//
//    Optional<T> find(Long userId, K key);
//
//    List<T> findAll(Long userId, Integer limit);
//
//    Optional<T> update(Long userId, T t);
//
//    boolean delete(Long userId, K key) {
//        try(var con = ConnectionManager.open()) {
//            eraser.delete(con, key);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return true;
//    }
//
//    boolean deleteList(Long userId, List<K> key);
//}
