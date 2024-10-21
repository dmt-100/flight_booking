//package ru.dmt100.flight_booking.entity.boardingPass.sql.enums;
//
//import ru.dmt100.flight_booking.sql.Query;
//
//public enum BoardingPassQuery implements Query {
//    CHECKING_BOOK_REF("CHECKING_BOOK_REF"),
//    CHECKING_FLIGHT_ID("CHECKING_FLIGHT_ID"),
//    NEW_BOOKING("NEW_BOOKING"),
//    BOOKING_BY_BOOK_REF("BOOKING_BY_BOOK_REF"),
//    BOOKINGS_BY_FLIGHT_ID("BOOKINGS_BY_FLIGHT_ID"),
//    ALL_BOOKINGS("ALL_BOOKINGS"),
//    UPDATE_BOOKING("UPDATE_BOOKING"),
//    DELETE_BOOKING_BY_BOOK_REF("DELETE_BOOKING_BY_BOOK_REF");
//
//    private final String queryKey;
//
//    BoardingPassQuery(String queryKey) {
//        this.queryKey = queryKey;
//    }
//
//    @Override
//    public String getQuery() {
//        return queryKey;
//    }
//
//}
