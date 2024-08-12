package ru.dmt100.flight_booking.ticket.dto.record;

public record PassengersInfoByFlightId(
        Long ticketNo,
        String bookRef,
        Long passengerId,
        String passengerName,
        String contactData) {
}
