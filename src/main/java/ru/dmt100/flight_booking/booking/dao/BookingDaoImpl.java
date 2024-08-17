package ru.dmt100.flight_booking.booking.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.booking.dataaccess.BookingEraser;
import ru.dmt100.flight_booking.booking.dataaccess.BookingLoaderByKey;
import ru.dmt100.flight_booking.booking.dataaccess.BookingLoaderFindAll;
import ru.dmt100.flight_booking.booking.dataaccess.BookingPersistent;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoRequest;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.dao.Dao;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.sql.provider.SqlQueryProvider;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;
import ru.dmt100.flight_booking.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Service("bookingDaoIml")
@Transactional(readOnly = true)
@Primary
public class BookingDaoImpl  implements Dao<Long, String, Integer, BookingDtoRequest, BookingDtoResponse> {
    private BookingLoaderByKey bookingLoader;
    private BookingLoaderFindAll bookingLoaderFindAll;
    private BookingPersistent bookingPersistent;
    private BookingEraser bookingEraser;
    private SqlQueryProvider sqlQuery;

    @Autowired
    public BookingDaoImpl(@Qualifier("bookingLoaderByKey") BookingLoaderByKey bookingLoader,
                          @Qualifier("bookingPersistent") BookingPersistent bookingPersistent,
                          @Qualifier("bookingEraser")  BookingEraser bookingEraser,
                          @Qualifier("bookingLoaderFindAll")  BookingLoaderFindAll bookingLoaderFindAll,

                          SqlQueryProvider sqlQuery) {
        this.sqlQuery = sqlQuery;
        this.bookingLoader = bookingLoader;
        this.bookingPersistent = bookingPersistent;
        this.bookingEraser = bookingEraser;
        this.bookingLoaderFindAll = bookingLoaderFindAll;
//        this.converter = converter;
    }

    @Override
    public Optional<BookingDtoResponse> save(Long userId, BookingDtoRequest bookingDtoRequest) {
        var bookRef = bookingDtoRequest.bookRef();

        try (var con = ConnectionManager.open()) {
            bookingPersistent.save(con, bookingDtoRequest);

            return find(userId, bookRef);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<BookingDtoResponse> find(Long userId, String bookRef) {
        Optional<BookingDtoResponse> booking;
        try (Connection con = ConnectionManager.open()) {

            booking = Optional.ofNullable(bookingLoader.load(con, bookRef)
                    .orElseThrow(() -> new NotFoundException("Booking " + bookRef + ", does not exist")));;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }


    @Override
    public List<BookingDtoResponse> findAll(Long userId, Integer limit) {
        List<BookingDtoResponse> bookings;
        try (var con = ConnectionManager.open()) {

            bookings = bookingLoaderFindAll.loadAll(con, limit);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookings;
    }

    @Override
    public Optional<BookingDtoResponse> update(Long userId, BookingDtoRequest booking) {
        Optional<BookingDtoResponse> bookingDtoResponse = null;
//        String bookRef = booking.bookRef();
//
//        try (var con = ConnectionManager.open();
//             var stmt = con.prepareStatement(sqlQuery.getQuery("UPDATE_BOOKING"))) {
//
//            if (!isBookingExist(con, bookRef)) {
//                throw new NotFoundException("Booking " + bookRef + ", does not exist");
//            } else {
//
//                Timestamp timestamp = Timestamp.from(booking.getBookDate().toInstant());
//
//                stmt.setTimestamp(1, timestamp);
//                stmt.setBigDecimal(2, booking.getTotalAmount());
//                stmt.setString(3, bookRef);
//                int row = stmt.executeUpdate();
//                if (row == 0) {
//                    throw new UpdateException("Failed to update a booking.");
//                } else {
//                    bookingDtoResponse = find(userId, bookRef);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return bookingDtoResponse;
    }

    @Override
    public boolean delete(Long userId, String bookRef) {
        try (var con = ConnectionManager.open()) {

            bookingEraser.delete(con, bookRef);

//            if (!isBookingExist(con, bookRef)) {
//                throw new NotFoundException("Booking: " + bookRef + ", does not exist");
//            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

//    @Override
    public boolean deleteList(Long userId, List<String> bookRefs) {
        for (String bookRef : bookRefs) {
            delete(userId, bookRef);
        }
        return true;
    }

    private Set<TicketLiteDtoResponse> getTicketsByBookRef(Connection con, String bookRef) {
        Set<TicketLiteDtoResponse> tickets = new HashSet<>();
//        TicketLiteDtoResponse ticket;
//
//        try (var stmt = con.prepareStatement(sqlQuery.getTICKETS_BY_BOOK_REF())) {
//            stmt.setQueryTimeout(1);
//            stmt.setString(1, bookRef);
//            var rs = stmt.executeQuery();
//            while (rs.next()) {
//                String ticketNo = rs.getString("ticket_no");
//                Long passengerId = Long.valueOf(rs.getString("passenger_id")
//                        .replace(" ", ""));
//                String passengerName = rs.getString("passenger_name");
//                String contactDataString = rs.getString("contact_data");
//                Map<String, String> contactData = new MapConverter().convertToEntityAttribute(contactDataString);
//
//                // added bpCompositeKeys
//                List<String> bpCompositeKeys = new ArrayList<>();
//                try (var stmt2 = con.prepareStatement(sqlQuery.getTICKET_NO_FLIGHT_ID_FROM_BOARDING_PASSES())) {
//                    stmt2.setString(1, ticketNo);
//                    var rs2 = stmt2.executeQuery();
//                    while (rs2.next()) {
//                        String formatted = rs2.getString(1) + "_" + rs2.getString(2);
//                        bpCompositeKeys.add(formatted);
//                    }
//                }
//
//                ticket = new TicketLiteDtoResponse(
//                        ticketNo,
//                        bookRef,
//                        passengerId,
//                        passengerName,
//                        contactData,
//                        bpCompositeKeys);
//                tickets.add(ticket);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return tickets;
    }

//    private boolean isBookingExist(Connection con, String bookRef) {
//        try (var stmt = con.prepareStatement(sqlQuery.getQuery("CHECKING_BOOK_REF"))) {
//
//            stmt.setString(1, bookRef);
//            var rs = stmt.executeQuery();
//
//            return rs.next();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}