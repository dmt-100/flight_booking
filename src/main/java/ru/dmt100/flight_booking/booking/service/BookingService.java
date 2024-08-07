package ru.dmt100.flight_booking.booking.service;

import ru.dmt100.flight_booking.booking.model.dto.BookingDtoResponse;
import ru.dmt100.flight_booking.booking.model.dto.BookingStatisticsDateDto;
import ru.dmt100.flight_booking.booking.model.dto.BookingStatisticsWeekDto;
import ru.dmt100.flight_booking.booking.model.dto.PassengerInfo;

import java.util.List;

public interface BookingService {

    List<PassengerInfo> findPassengersInfoByBookingId(Long userId, String bookRef);

    List<PassengerInfo> findPassengersInfoByFlightId(Long userId, Long flightId);

    List<BookingDtoResponse> getBookingsByFlightId(Long userId, Long flightId);
    List<PassengerInfo> getAllBookingsByDate(Long userId);

    List<BookingStatisticsDateDto> getStats_RevenueByDate(Long userId);

    List<BookingStatisticsWeekDto> getStats_RevenueByWeek(Long userId);

}
