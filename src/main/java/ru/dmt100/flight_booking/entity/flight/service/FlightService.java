package ru.dmt100.flight_booking.entity.flight.service;

import ru.dmt100.flight_booking.entity.flight.dto.record.*;

import java.util.List;

public interface FlightService {

    List<String> findAllTicketsByFlightId(Long userId, Long flightId);

    List<FlightCountByStatus> getFlightCountByStatus();

    List<FlightsCountByMonth> getFlightsCountByMonth();

    List<DelayedFlightsWithPassengers> getDelayedFlightsWithPassengers();

    List<FlightsDelayedMoreThanTwoHours> getFlightsDelayedMoreThanTwoHours();

    List<MostPopularRoutes> getMostPopularRoutes();

    List<AvgDelayByDayOfWeek> getAvgDelayByDayOfWeek();
}
