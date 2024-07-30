package ru.dmt100.bookingflight.booking.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.bookingflight.booking.model.Booking;
import ru.dmt100.bookingflight.booking.model.dto.PassengerInfo;
import ru.dmt100.bookingflight.exception.NotFoundException;
import ru.dmt100.bookingflight.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private static final String CHECK_BOOKING_EXISTS = """
            SELECT 1 FROM bookings WHERE book_ref = ?
            """;

    private static final String FIND_PASSENGERS_BY_BOOKING = """
            SELECT t.ticket_no, t.passenger_id, t.passenger_name, t.contact_data
            FROM tickets t
            JOIN bookings b ON t.book_ref = b.book_ref 
            WHERE t.book_ref = ?""";


    @Override
    public ResponseEntity<Booking> save(Long userId, Booking booking) {
        return null;
    }

    @Override
    public Booking findBookingById(Long userId, String bookRef) {
        return null;
    }

    @Override
    public List<PassengerInfo> findPassengersByBooking(Long userId, String bookRef) {
        List<PassengerInfo> passengerInfos = new ArrayList<>()  ;

        try (var connection = ConnectionManager.open();
             var checkStatement = connection.prepareStatement(CHECK_BOOKING_EXISTS);
        ) {
            checkStatement.setString(1, bookRef);
            var checkResultSet = checkStatement.executeQuery();
            if (!checkResultSet.next()) {
                throw new NotFoundException("Booking " + bookRef + ", does not exist");
            } else {
                try (var statement = connection.prepareStatement(FIND_PASSENGERS_BY_BOOKING)) {
                    statement.setString(1, bookRef);
                    var rs = statement.executeQuery();
                    while (rs.next()) {
                        Long ticketNo = rs.getLong("ticket_no");
                        String passengerId = rs.getString("passenger_id");
                        String passengerName = rs.getString("passenger_name");
                        String contactData = rs.getString("contact_data");

                        PassengerInfo info = new PassengerInfo(ticketNo, passengerId, passengerName, contactData);

                        passengerInfos.add(info);
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return passengerInfos;
    }

    @Override
    public List<Booking> findBookingsByFlightId(Long userId, Long flightId) {
        return null;
    }

    @Override
    public List<Booking> findAll(Long userId) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(Long userId, String bookRef) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long userId, String bookRef) {
        return null;
    }
}
