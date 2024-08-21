package ru.dmt100.flight_booking.entity.ticket.service;

import ru.dmt100.flight_booking.entity.ticket.model.dto.TicketDto;
import ru.dmt100.flight_booking.entity.ticket.model.dto.record.PassengersInfoByFlightId;
import ru.dmt100.flight_booking.entity.ticket.model.dto.record.TicketOnScheduledFlightsByTimeRange;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketService {

    List<Optional<TicketDto>> findPassengersInfoByListOfTicketNos(
            Long userId, List<String> passengersTicketNos);

    List<PassengersInfoByFlightId> findPassengersInfoByFlightId(Long userId, Long flightId);

    List<TicketOnScheduledFlightsByTimeRange> getCountTicketsOnScheduledFlightsByTimeRange(
            Long userId, LocalDateTime start, LocalDateTime end);



}
