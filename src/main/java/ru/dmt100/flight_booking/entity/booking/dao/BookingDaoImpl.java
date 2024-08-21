package ru.dmt100.flight_booking.entity.booking.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.dao.Dao;
import ru.dmt100.flight_booking.entity.booking.model.Booking;
import ru.dmt100.flight_booking.entity.booking.model.dataaccess.BookingEraser;
import ru.dmt100.flight_booking.entity.booking.model.dataaccess.BookingPersistent;
import ru.dmt100.flight_booking.entity.booking.model.dataaccess.BookingReader;
import ru.dmt100.flight_booking.entity.booking.model.dto.BookingDto;
import ru.dmt100.flight_booking.entity.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("bookingDaoIml")
@Transactional(readOnly = true)
@Primary
public class BookingDaoImpl implements Dao<Long, Integer, String, Booking, BookingDto, BookingDtoResponse> {
    private final BookingReader bookingReader;
    private final BookingPersistent bookingPersistent;
    private final BookingEraser bookingEraser;
//    private final BookingDtoConverter bookingDtoConverter;

    @Autowired
    public BookingDaoImpl(@Qualifier("bookingReader") BookingReader bookingReader,
                          @Qualifier("bookingPersistent") BookingPersistent bookingPersistent,
                          @Qualifier("bookingEraser") BookingEraser bookingEraser
//                          @Qualifier("bookingDtoConverter") BookingDtoConverter bookingDtoConverter
    ) {
        this.bookingReader = bookingReader;
        this.bookingPersistent = bookingPersistent;
        this.bookingEraser = bookingEraser;
//        this.bookingDtoConverter = bookingDtoConverter;
    }

    @Override
    public Optional<BookingDtoResponse> save(Long userId, BookingDto bookingDto) {
        var bookRef = bookingDto.bookRef();

        try (var con = ConnectionManager.open()) {
            bookingPersistent.save(con, bookingDto);

            return find(userId, bookRef);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<BookingDtoResponse> find(Long userId, String bookRef) {
        try (Connection con = ConnectionManager.open()) {
            Object result = bookingReader.load(con, bookRef)
                    .orElseThrow(() -> new NotFoundException("Booking " + bookRef + ", does not exist"));

            return Optional.of((BookingDtoResponse) result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<BookingDto> findAll(Long userId, Integer limit) {
        try (var con = ConnectionManager.open()) {

            return bookingReader.loadAll(con, limit);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<BookingDtoResponse> update(Long userId, BookingDto bookingDto) {
        var bookRef = bookingDto.bookRef();

        try (var con = ConnectionManager.open()) {
            bookingEraser.delete(con, bookRef);

            bookingPersistent.save(con, bookingDto);

            return (Optional<BookingDtoResponse>) bookingReader.load(con, bookRef);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long userId, String bookRef) {
        try(var con = ConnectionManager.open()) {
            bookingEraser.delete(con, bookRef);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean deleteList(Long userId, List<String> bookRefs) {
        for (String bookRef : bookRefs) {
            delete(userId, bookRef);
        }
        return true;
    }

}