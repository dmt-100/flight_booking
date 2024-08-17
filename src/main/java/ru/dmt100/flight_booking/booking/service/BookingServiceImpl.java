package ru.dmt100.flight_booking.booking.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmt100.flight_booking.booking.dataaccess.BookingLoaderByKey;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoRequest;
import ru.dmt100.flight_booking.booking.sql.BookingSql;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service("bookingServiceImpl")
public class BookingServiceImpl implements BookingService {
    private final BookingSql sqlQuery;
    private final BookingLoaderByKey bookingFetcher;

    public List<BookingDtoRequest> findBookingsByFlightId(Long userId, Long flightId) {
        List<BookingDtoRequest> bookingDtoRequestRespons = new ArrayList<>();
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
        return bookingDtoRequestRespons;
    }



}
