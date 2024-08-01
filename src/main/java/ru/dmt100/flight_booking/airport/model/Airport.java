package ru.dmt100.flight_booking.airport.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dmt100.flight_booking.flight.model.Flight;

import java.awt.*;
import java.util.Set;

@Entity
@Table(name = "airports_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Airport {

    @Id
    @Column(name = "airport_code", length = 3, nullable = false)
    String airportCode;

    @Column(name = "airport_name", columnDefinition = "jsonb", nullable = false)
    String airportName;

    @Column(name = "city", columnDefinition = "jsonb", nullable = false)
    String city;

    @Column(name = "coordinates", nullable = false)
    Point coordinates;

    @Column(name = "timezone", nullable = false)
    String timezone;

    @OneToMany(mappedBy = "departureAirport",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    Set<Flight> departingFlights;

    @OneToMany(mappedBy = "arrivalAirport",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    Set<Flight> arrivingFlights;
}
