package com.example.booking_test.model;

import com.example.booking_test.model.enums.FareConditions;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "ticket_flights")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class TicketFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fare_conditions", nullable = false)
    FareConditions fareConditions;

    @NonNull
    @Column(name = "amount", nullable = false)
    Double amount;

    @ManyToOne
    @JoinColumn(name = "ticket_no", referencedColumnName = "ticket_no", insertable = false, updatable = false)
    Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "flight_id", insertable = false, updatable = false)
    Flight flight;


}
