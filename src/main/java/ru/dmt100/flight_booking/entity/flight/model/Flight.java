package ru.dmt100.flight_booking.entity.flight.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dmt100.flight_booking.entity.aircraft.model.Aircraft;
import ru.dmt100.flight_booking.entity.airport.model.Airport;
import ru.dmt100.flight_booking.entity.ticketFlight.TicketFlight;
import ru.dmt100.flight_booking.enums.Status;

import java.time.ZonedDateTime;
import java.util.Set;


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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_id_seq")
    @SequenceGenerator(name = "flight_id_seq", sequenceName = "flight_id_seq", allocationSize = 1)
    @Column(name = "flight_id")
    Long flightId;

    @Column(name = "flight_no", length = 6, nullable = false)
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

    @Column(name = "status", length = 20, nullable = false)
    Status status;

    @ManyToOne
    @JoinColumn(name = "aircraft_code", nullable = false)
    Aircraft aircraftCode;

    @Column(name = "actual_departure")
    ZonedDateTime actualDeparture;

    @Column(name = "actual_arrival")
    ZonedDateTime actualArrival;

    @OneToMany(mappedBy = "flight",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    Set<TicketFlight> ticketFlights;

}
