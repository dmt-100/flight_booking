package com.example.booking_test.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "flight_id")
    Long flightId;

    @Column(name = "flight_no", nullable = false)
    @NonNull
    String flightNumber;

    @Column(name = "scheduled_departure", nullable = false)
    @NonNull
    ZonedDateTime scheduledDeparture;

    @Column(name = "scheduled_arrival", nullable = false)
    @NonNull
    ZonedDateTime scheduledArrival;

    @NonNull
    @OneToOne
    @Column(name = "departure_airport", nullable = false, length = 3)
    Airport departureAirport;

    @NonNull
    @OneToOne
    @Column(name = "arrival_airport", nullable = false, length = 3)
    Airport arrivalAirport;

    @Column(name = "status", nullable = false)
    @NonNull
    String status;

    @Column(name = "aircraft_code", nullable = false, length = 3)
    @NonNull
    @OneToOne
    Aircraft aircraftCode;

    @Column(name = "actual_departure")
    ZonedDateTime actualDeparture;

    @Column(name = "actual_arrival")
    ZonedDateTime actualArrival;
}
