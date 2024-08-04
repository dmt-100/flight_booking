package ru.dmt100.flight_booking.ticket.service;

import org.springframework.http.ResponseEntity;
import ru.dmt100.flight_booking.booking.model.Booking;
import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.ticket.dto.TicketDtoResponse;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;
import ru.dmt100.flight_booking.ticket.model.Ticket;

import java.util.List;

public interface TicketService {

    TicketLiteDtoResponse save(Long userId, Ticket ticket);

    TicketLiteDtoResponse getTicketLiteDtoResponse(Long userId, String ticketNo);


    List<TicketLiteDtoResponse> findAllTicketsLite(Long userId);

    BookingDtoResponse updateTicketLite(Long userId, String bookRef, Booking booking);

    ResponseEntity<?> delete(Long userId, String bookRef);


    TicketDtoResponse getTicketDtoResponse(Long userId, Long ticketNo);

    TicketDtoResponse getTicketById(Long userId, Long ticketNo);



}
