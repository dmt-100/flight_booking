package com.example.booking_test.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "flights")
@ToString
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "flight_id")
    Long flightId;

    @Column(name = "flight_no", nullable = false)
    @NonNull
    String flightNo;

    @Column(name = "scheduled_departure", nullable = false)
    @NonNull
    ZonedDateTime scheduledDeparture;

    @Column(name = "scheduled_arrival", nullable = false)
    @NonNull
    ZonedDateTime scheduledArrival;

    @NonNull
    @ManyToOne
    @Column(name = "departure_airport", nullable = false, length = 3)
    Airport departureAirport;

    @NonNull
    @ManyToOne
    @Column(name = "arrival_airport", nullable = false, length = 3)
    Airport arrivalAirport;

    @Column(name = "status", nullable = false)
    @NonNull
    String status;

    @JoinColumn(name = "aircraft_code", nullable = false)
    @NonNull
    @ManyToOne
    Aircraft aircraftCode;

    @Column(name = "actual_departure")
    ZonedDateTime actualDeparture;

    @Column(name = "actual_arrival")
    ZonedDateTime actualArrival;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TicketFlight> ticketFlights;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BoardingPass> boardingPasses;

}
