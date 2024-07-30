package ru.dmt100.bookingflight.ticketFlight.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dmt100.bookingflight.boardingPass.model.BoardingPass;
import ru.dmt100.bookingflight.enums.FareConditions;
import ru.dmt100.bookingflight.flight.model.Flight;
import ru.dmt100.bookingflight.ticket.model.Ticket;

import java.math.BigDecimal;
import java.util.Set;

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
    @ManyToOne
    @JoinColumn(name = "ticket_no",
            referencedColumnName = "ticket_no",
            insertable = false,
            updatable = false)
    Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "flight_id",
            referencedColumnName = "flight_id",
            insertable = false,
            updatable = false)
    Flight flight;

    @Enumerated(EnumType.STRING)
    @Column(name = "fare_conditions", nullable = false)
    FareConditions fareConditions;

    @Column(name = "amount", nullable = false)
    BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "ticket_no", insertable = false, updatable = false)
    private BoardingPass boardingPass;

    @OneToMany(mappedBy = "ticketFlight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Ticket> tickets;


}
