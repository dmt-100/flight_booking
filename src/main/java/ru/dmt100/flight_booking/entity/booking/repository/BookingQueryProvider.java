//package ru.dmt100.flight_booking.entity.booking.repository;
//
//import org.springframework.stereotype.Component;
//import ru.dmt100.flight_booking.entity.booking.repository.sql.BookingQuery;
//import ru.dmt100.flight_booking.sql.provider.QueryProvider;
//
//@Component("bookingQueryProvider")
//public class BookingQueryProvider implements QueryProvider {
//
//    @Override
//    public QueryProvider getQuery(String query) {
//        return (QueryProvider) new BookingQuery(query);
//    }
//}
