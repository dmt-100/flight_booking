package ru.dmt100.flight_booking.ticket.service;

import ru.dmt100.flight_booking.ticket.dto.record.PassengersInfoByFlightId;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;
import ru.dmt100.flight_booking.ticket.dto.record.TicketOnScheduledFlightsByTimeRange;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketService {

    List<Optional<TicketLiteDtoResponse>> findPassengersInfoByListOfTicketNos(
            Long userId, List<String> passengersTicketNos);

    List<PassengersInfoByFlightId> findPassengersInfoByFlightId(Long userId, Long flightId);
    List<TicketOnScheduledFlightsByTimeRange> getCountTicketsOnScheduledFlightsByTimeRange(
            Long userId, LocalDateTime start, LocalDateTime end);



}
