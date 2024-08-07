package ru.dmt100.flight_booking.booking.model.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingStatisticsDateDto {
    LocalDate date ;
    String month;
    Long totalBookings;
    Long totalRevenue;
    Long avgBookingAmount;
}
