package ru.dmt100.flight_booking.entity.ticket.model.dto.record;

public record PassengersInfoByFlightId(
        Long ticketNo,
        String bookRef,
        Long passengerId,
        String passengerName,
        String contactData) {
}
