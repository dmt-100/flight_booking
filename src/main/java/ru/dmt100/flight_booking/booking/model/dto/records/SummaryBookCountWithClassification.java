package ru.dmt100.flight_booking.booking.model.dto.records;

public record SummaryBookCountWithClassification(
        Integer bookCount,
        String costCategory
) {}
