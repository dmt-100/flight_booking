package ru.dmt100.bookingflight.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PassengerInfo {
    private Long ticketNo;
    private String passengerId;
    private String passengerName;
    private String contactData;
}
