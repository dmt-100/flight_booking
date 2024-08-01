package ru.dmt100.flight_booking.ticket.service;

import org.springframework.http.ResponseEntity;
import ru.dmt100.flight_booking.ticket.model.Ticket;
import ru.dmt100.flight_booking.ticket.dto.TicketDtoResponse;

public interface TicketService {

    ResponseEntity<?> save(Long userId, Ticket ticket);

    TicketDtoResponse findTicketById(Long userId, Long ticketNo);

}
