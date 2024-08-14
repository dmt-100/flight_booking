package ru.dmt100.flight_booking.booking.fetcher;

import ru.dmt100.flight_booking.booking.model.dto.BookingLiteDto;
import ru.dmt100.flight_booking.fetcher.EntityFetcher;
import ru.dmt100.flight_booking.mapper.EntityMapper;
import ru.dmt100.flight_booking.sql.SqlQueryProvider;

abstract class BookingFetcher extends EntityFetcher<BookingLiteDto, String> {

    public BookingFetcher(EntityMapper<BookingLiteDto> mapper, SqlQueryProvider sqlQueryProvider) {
        super(mapper, sqlQueryProvider, "TICKETS_BY_BOOK_REF");
    }
}

