package ru.dmt100.flight_booking.entity.booking.repository;//package ru.dmt100.flight_booking.booking.mapper;
//

import org.springframework.stereotype.Repository;
import ru.dmt100.flight_booking.entity.booking.model.dto.BookingDto;
import ru.dmt100.flight_booking.exception.DuplicateBookingException;
import ru.dmt100.flight_booking.exception.UpdateException;
import ru.dmt100.flight_booking.repository.mapper.Persistent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository("bookingInserter")
public class BookingInserter implements Persistent<BookingDto> {

    @Override
    public void insertToDb(PreparedStatement stmt, BookingDto bookingDto) {
        try {
            var bookDate = Timestamp.from(bookingDto.bookDate().toInstant());
            stmt.setString(1, bookingDto.bookRef());
            stmt.setTimestamp(2, bookDate);
            stmt.setBigDecimal(3, bookingDto.totalAmount());
            int row = stmt.executeUpdate();
            if (row == 0) {
                throw new UpdateException("Failed to update a booking.");
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new DuplicateBookingException(
                        "Booking with reference " + bookingDto.bookRef() + " already exists.");
            }
            throw new RuntimeException("Error inserting booking", e);
        }

    }
}
