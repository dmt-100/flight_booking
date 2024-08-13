package ru.dmt100.flight_booking.boardingPass.model.dto;

public record BoardingPassDto(
        String ticketNo,
        Long flightId,
        Short boardingNo,
        String seatNo
) {
}
