package ru.dmt100.flight_booking.entity.boardingPass.model.dto;

public record BoardingPassDto(
        String ticketNo,
        Long flightId,
        Short boardingNo,
        String seatNo
) {
}
