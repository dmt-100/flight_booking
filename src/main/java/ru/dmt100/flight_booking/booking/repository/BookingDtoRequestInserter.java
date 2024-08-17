package ru.dmt100.flight_booking.booking.repository;//package ru.dmt100.flight_booking.booking.mapper;
//

import org.springframework.stereotype.Repository;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoRequest;
import ru.dmt100.flight_booking.exception.UpdateException;
import ru.dmt100.flight_booking.repository.mapper.Persistent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository("bookingDtoRequestInserter")
public class BookingDtoRequestInserter implements Persistent<BookingDtoRequest> {

    @Override
    public void insertToDb(PreparedStatement stmt, BookingDtoRequest bookingDtoRequest) {
        try {
            var bookDate = Timestamp.from(bookingDtoRequest.bookDate().toInstant());
            stmt.setString(1, bookingDtoRequest.bookRef());
            stmt.setTimestamp(2, bookDate);
            stmt.setBigDecimal(3, bookingDtoRequest.totalAmount());
            int row = stmt.executeUpdate();
            if (row == 0) {
                throw new UpdateException("Failed to update a booking.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
