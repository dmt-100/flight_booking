package ru.dmt100.flight_booking.ticket.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.dmt100.flight_booking.booking.model.Booking;
import ru.dmt100.flight_booking.ticketFlight.model.TicketFlight;

@Entity
@Table(name = "tickets")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Ticket {

    @Id
    @Column(name = "ticket_no", nullable = false)
    Long ticketNo;

    @Column(name = "book_ref", insertable=false, updatable=false, nullable = false)
    String bookRef;

    @Column(name = "passenger_id", nullable = false, length = 20)
    Long passengerId;

    @Column(name = "passenger_name", nullable = false, length = 100)
    String passengerName;

    @Column(name = "contact_data", columnDefinition = "jsonb")
    String contactData;

    @ManyToOne
    @JoinColumn(name = "book_ref", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Booking booking;

    @ManyToOne
    @JoinColumn(name = "ticket_no", insertable = false, updatable = false)
    TicketFlight ticketFlight;

}
