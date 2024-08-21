package ru.dmt100.flight_booking.entity.booking.model.stats;

public record SummaryBookCountWithClassification(
        Integer bookCount,
        String costCategory
) {}
