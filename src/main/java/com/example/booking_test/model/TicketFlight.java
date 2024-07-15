package com.example.booking_test.model;

import com.example.booking_test.model.enums.FareConditions;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ticket_flights")
public class TicketFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_no", nullable = false)
    @NonNull
    private String ticketNo;

    @Column(name = "flight_id", nullable = false)
    @NonNull
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "fare_conditions", nullable = false)
    @NonNull
    private FareConditions fareConditions;

    @Column(name = "amount", nullable = false)
    @NonNull
    private BigDecimal amount;
}
