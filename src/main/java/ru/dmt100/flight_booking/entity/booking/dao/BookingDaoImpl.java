package ru.dmt100.flight_booking.entity.booking.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.dao.impl.DaoImpl;
import ru.dmt100.flight_booking.entity.booking.crud.BookingEraser;
import ru.dmt100.flight_booking.entity.booking.crud.BookingPersistent;
import ru.dmt100.flight_booking.entity.booking.crud.BookingReader;

@Slf4j
@Service("bookingDaoImpl")
@Transactional(readOnly = true)
@Primary
public class BookingDaoImpl extends DaoImpl  {
//    private final BookingReader bookingReader;
//    private final BookingPersistent bookingPersistent;
//    private final BookingEraser bookingEraser;

    @Autowired
    public BookingDaoImpl(@Qualifier("bookingReader") BookingReader bookingReader,
                          @Qualifier("bookingPersistent") BookingPersistent bookingPersistent,
                          @Qualifier("bookingEraser") BookingEraser bookingEraser
    ) {
        super(bookingReader, bookingPersistent, bookingEraser);
//        this.bookingReader = bookingReader;
//        this.bookingPersistent = bookingPersistent;
//        this.bookingEraser = bookingEraser;
    }
//
//    @Override
//    public Optional<BookingDtoResponse> save(Long userId, BookingDto bookingDto) {
//        var bookRef = bookingDto.bookRef();
//
//        try (var con = ConnectionManager.open()) {
//            bookingPersistent.save(con, bookingDto);
//
//            return find(userId, bookRef);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public Optional<BookingDtoResponse> find(Long userId, String bookRef) {
//        try (Connection con = ConnectionManager.open()) {
//            Object result = bookingReader.load(con, bookRef)
//                    .orElseThrow(() -> new NotFoundException("Booking " + bookRef + ", does not exist"));
//
//            return Optional.of((BookingDtoResponse) result);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    @Override
//    public List<BookingDto> find(Long userId,
//                                 OffsetDateTime startTime,
//                                 OffsetDateTime endTime,
//                                    Integer limit) {
//        try (var con = ConnectionManager.open()) {
//
//            return bookingReader.load(con, startTime, endTime, limit);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public Optional<BookingDtoResponse> update(Long userId, BookingDto bookingDto) {
//        var bookRef = bookingDto.bookRef();
//
//        try (var con = ConnectionManager.open()) {
//            bookingEraser.delete(con, bookRef);
//
//            bookingPersistent.save(con, bookingDto);
//
//            return (Optional<BookingDtoResponse>) bookingReader.load(con, bookRef);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public boolean delete(Long userId, String bookRef) {
//        boolean isExist;
//        try(var con = ConnectionManager.open()) {
//           bookingEraser.delete(con, bookRef);
//
//            isExist = new BookingVerifier().isExist(con, bookRef);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return isExist;
//    }
//
//    @Override
//    public Map<String, Boolean> deleteList(Long userId, List<String> bookRefs) {
//        Map<String, Boolean> deletedBookings = new HashMap<>();
//        for(String bookRef : bookRefs) {
//            try(var con = ConnectionManager.open()) {
//                deletedBookings.put(bookRef, bookingEraser.delete(con, bookRef));
//
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return deletedBookings;
//    }

}