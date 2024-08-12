package ru.dmt100.flight_booking.flight.service;

import ru.dmt100.flight_booking.flight.dto.record.*;

import java.util.List;

public interface FlightService {
    List<FlightCountByStatus> getFlightCountByStatus();

    List<FlightsCountByMonth> getFlightsCountByMonth();

    List<DelayedFlightsWithPassengers> getDelayedFlightsWithPassengers();

    List<FlightsDelayedMoreThanTwoHours> getFlightsDelayedMoreThanTwoHours();

    List<MostPopularRoutes> getMostPopularRoutes();

    List<AvgDelayByDayOfWeek> getAvgDelayByDayOfWeek();
}
