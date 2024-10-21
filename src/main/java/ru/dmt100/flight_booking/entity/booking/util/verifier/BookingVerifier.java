package ru.dmt100.flight_booking.entity.booking.util.verifier;

import org.springframework.beans.factory.annotation.Qualifier;
import ru.dmt100.flight_booking.entity.booking.repository.sql.BookingQuery;
import ru.dmt100.flight_booking.repository.verifier.impl.VerifierImpl;

public class BookingVerifier extends VerifierImpl {

    public BookingVerifier(@Qualifier("bookingQuery") BookingQuery bookingQuery) {
        super(bookingQuery.getCHECKING_BOOK_REF());
    }
}
