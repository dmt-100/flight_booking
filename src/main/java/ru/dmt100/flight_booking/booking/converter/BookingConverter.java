//package ru.dmt100.flight_booking.booking.converter;
//
//import org.springframework.stereotype.Component;
//import ru.dmt100.flight_booking.booking.model.dto.BookingDtoRequest;
//import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
//import ru.dmt100.flight_booking.converter.RequestToResponseConverter;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Component("converter")
//public class BookingConverter implements RequestToResponseConverter<BookingDtoRequest, BookingDtoResponse> {
//
//    @Override
//    public BookingDtoResponse convertToResponse(BookingDtoRequest bookingDtoRequest) {
////        Set<String> ticketNos = bookingDtoRequest.getTickets().stream()
////                .map(ticket -> String.format("013%d", ticket.getTicketNo())).collect(Collectors.toSet());
//        Set<String> ticketNos= new HashSet<>();
//        return new BookingDtoResponse(
//                bookingDtoRequest.bookRef(),
//                bookingDtoRequest.bookDate(),
//                bookingDtoRequest.totalAmount(),
//                ticketNos
//        );
//    }
//
//    @Override
//    public BookingDtoResponse convertToRequest(BookingDtoRequest bookingDtoRequest) {
//
//        return null;
//    }
//}
