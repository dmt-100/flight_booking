package ru.dmt100.flight_booking.ticket.service;

import ru.dmt100.flight_booking.ticket.dto.TicketDtoResponse;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;
import ru.dmt100.flight_booking.ticket.model.Ticket;

public interface TicketService {

    TicketLiteDtoResponse save(Long userId, Ticket ticket);

    TicketLiteDtoResponse getTicketLiteDtoResponse(Long userId, String ticketNo);

    TicketDtoResponse getTicketDtoResponse(Long userId, Long ticketNo);

    TicketDtoResponse findTicketById(Long userId, Long ticketNo);

}
