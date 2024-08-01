package ru.dmt100.flight_booking.flight.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dmt100.flight_booking.ticketFlight.model.TicketFlight;
import ru.dmt100.flight_booking.aircraft.model.Aircraft;
import ru.dmt100.flight_booking.airport.model.Airport;
import ru.dmt100.flight_booking.boardingPass.model.BoardingPass;

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
    String flightNo;

    @Column(name = "scheduled_departure", nullable = false)
    ZonedDateTime scheduledDeparture;

    @Column(name = "scheduled_arrival", nullable = false)
    ZonedDateTime scheduledArrival;

    @ManyToOne
    @JoinColumn(name = "departure_airport", nullable = false)
    Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport", nullable = false)
    Airport arrivalAirport;

    @Column(name = "status", nullable = false)
    String status;

    @ManyToOne
    @JoinColumn(name = "aircraft_code", nullable = false)
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
