package ru.dmt100.flight_booking.booking.model.stats;

public record SummaryBookCountWithClassification(
        Integer bookCount,
        String costCategory
) {}
