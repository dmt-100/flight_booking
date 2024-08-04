package ru.dmt100.flight_booking.ticket.service;

import org.springframework.http.ResponseEntity;
import ru.dmt100.flight_booking.ticket.dto.TicketDtoResponse;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;
import ru.dmt100.flight_booking.ticket.model.Ticket;

import java.util.List;

public interface TicketService {

    TicketLiteDtoResponse save(Long userId, Ticket ticket);

    TicketLiteDtoResponse getTicketLiteDtoResponse(Long userId, String ticketNo);


    List<TicketLiteDtoResponse> findAllTicketsLite(Long userId);


    TicketLiteDtoResponse update(Long userId, Ticket ticket);

    ResponseEntity<?> delete(Long userId, String ticketNo);


    TicketDtoResponse getTicketDtoResponse(Long userId, Long ticketNo);

    TicketDtoResponse getTicketById(Long userId, Long ticketNo);



}
