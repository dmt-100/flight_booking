package com.example.booking_test.model;

import com.example.booking_test.model.enums.FareConditions;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ticket_flights")
public class TicketFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_no", insertable = false, updatable = false)
    Ticket ticketNumber;

    @ManyToOne
    @JoinColumn(name = "flight_no", insertable = false, updatable = false)
    Flight flightId;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fare_conditions")
    FareConditions fareConditions;

    @NonNull
    @Column(name = "amount", nullable = false)
    BigDecimal amount;
}
