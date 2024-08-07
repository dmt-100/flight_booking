package ru.dmt100.flight_booking.booking.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingStatisticsWeekDto {
    Integer weekOfYear;
    Long totalBookings;
    Long totalRevenue;
    Long avgBookingAmount;

}
