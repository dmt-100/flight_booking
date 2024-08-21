package ru.dmt100.flight_booking.entity.booking.converter;

import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.entity.booking.model.Booking;
import ru.dmt100.flight_booking.entity.booking.model.dto.BookingDto;

import java.util.HashSet;

@Component("bookingDtoConverter")
public class BookingDtoConverter {

    public Booking convertToEntity(BookingDto dto) {
        return new Booking(
                dto.bookRef(),
                dto.bookDate(),
                dto.totalAmount(),
                new HashSet<>()
        );
    }

    public BookingDto convertToDto(Booking booking) {
        return new BookingDto(
                booking.getBookRef(),
                booking.getBookDate(),
                booking.getTotalAmount()
        );
    }
}

