package com.example.booking_test.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "boarding_passes")
public class BoardingPass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_no", insertable = false, updatable = false)
    TicketFlight ticketNumber;

    @Nonnull
    @Column(name = "flight_id", nullable = false)
    Long flightId;

    @Nonnull
    @Column(name = "boarding_no", nullable = false)
    int boardingNumber;

    @Nonnull
    @Column(name = "seat_no", nullable = false, length = 4)
    String seatNumber;



}
