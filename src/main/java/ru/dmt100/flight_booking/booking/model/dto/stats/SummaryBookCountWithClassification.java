package ru.dmt100.flight_booking.booking.model.dto.stats;

public record SummaryBookCountWithClassification(
        Integer bookCount,
        String costCategory
) {}
