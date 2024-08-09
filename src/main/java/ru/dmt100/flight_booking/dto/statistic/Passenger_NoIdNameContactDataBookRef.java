package ru.dmt100.flight_booking.dto.statistic;

public record Passenger_NoIdNameContactDataBookRef(
        Long ticketNo,
        Long passengerId,
        String passengerName,
        String contactData,
        String bookRef
) {
}
