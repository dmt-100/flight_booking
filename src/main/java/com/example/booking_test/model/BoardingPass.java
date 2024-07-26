package com.example.booking_test.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "boarding_passes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class BoardingPass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_no", insertable = false, updatable = false)
    TicketFlight ticketNo;

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "flight_id", insertable = false, updatable = false)
    Flight  flight;

    @Nonnull
    @Column(name = "boarding_no", nullable = false)
    Integer boardingNo;

    @Nonnull
    @Column(name = "seat_no", nullable = false, length = 4)
    String seatNo;



}
