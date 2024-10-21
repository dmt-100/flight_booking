package ru.dmt100.flight_booking.entity.booking.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.dmt100.flight_booking.entity.booking.model.Booking;
import ru.dmt100.flight_booking.entity.booking.crud.BookingReader;

import java.util.ArrayList;
import java.util.List;

@Service("bookingServiceImpl")
public class BookingServiceImpl implements BookingService {
//    private final BookingSqlQueryInterface bookingSqlQueryInterface;
    private final BookingReader bookingFetcher;

    public BookingServiceImpl(
//            @Qualifier("bookingSqlQuery") BookingSqlQueryInterface bookingSqlQueryInterface,
                              @Qualifier("bookingReader") BookingReader bookingFetcher) {
//        this.bookingSqlQueryInterface = bookingSqlQueryInterface;
        this.bookingFetcher = bookingFetcher;
    }

    public List<Booking> findBookingsByFlightId(Long userId, Long flightId) {
        List<Booking> bookingResponse = new ArrayList<>();
//        Optional<BookingDtoResponse> bookingDtoResponse;
//
//        try (var con = ConnectionManager.open();
//             var checkStmt = con.prepareStatement(sqlQuery.getCHECKING_FLIGHT_ID());
//             var stmt = con.prepareStatement(sqlQuery.getBOOKINGS_BY_FLIGHT_ID())) {
//
//            checkStmt.setLong(1, flightId);
//            var checkResultSet = checkStmt.executeQuery();
//            if (!checkResultSet.next()) {
//                throw new NotFoundException("Flight " + flightId + ", does not exist");
//            } else {
//                stmt.setLong(1, flightId);
//                var rs = stmt.executeQuery();
//
//                while (rs.next()) {
//                    bookingDtoResponse = bookingFetcher.fetch(con, rs.getString(1));
//                    bookingDtoResponse.ifPresent(bookingDtoResponses::add);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return bookingResponse;
    }



}
