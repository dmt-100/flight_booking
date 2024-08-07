//package ru.dmt100.flight_booking.booking.dao;
//
//import org.springframework.http.ResponseEntity;
//import ru.dmt100.flight_booking.booking.model.Booking;
//import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
//import ru.dmt100.flight_booking.booking.model.dto.BookingLiteDtoResponse;
//
//import java.util.List;
//
//public interface BookingDao {
//
//    BookingDtoResponse save(Long userId, Booking booking);
//
//    BookingDtoResponse get(Long userId, boolean withTickets, String bookRef);
//
//
//
//    List<BookingLiteDtoResponse> findAll(Long userId);
//
//    BookingDtoResponse update(Long userId, String bookRef, Booking booking);
//
//    ResponseEntity<?> delete(Long userId, String bookRef);
//}
