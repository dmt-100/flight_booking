package ru.dmt100.flight_booking.booking.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PassengerInfo {
    private String bookRef;
    private Long ticketNo;

    private String passengerId;
    private String passengerName;
    private String contactData;


    public PassengerInfo(Long ticketNo, String passengerId, String passengerName, String contactData) {
        this.ticketNo = ticketNo;
        this.passengerId = passengerId;
        this.passengerName = passengerName;
        this.contactData = contactData;
    }
}
