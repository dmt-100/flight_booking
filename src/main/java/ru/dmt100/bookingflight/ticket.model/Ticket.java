package ru.dmt100.bookingflight.ticket.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dmt100.bookingflight.booking.model.Booking;
import ru.dmt100.bookingflight.ticketFlight.model.TicketFlight;

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
    String ticketNo;

    @PrimaryKeyJoinColumn
    @OneToOne
    Booking bookRef;

    @Column(name = "passenger_id", nullable = false, length = 20)
    String passengerId;

    @Column(name = "passenger_name", nullable = false, length = 100)
    String passengerName;

    @Column(name = "contact_data", columnDefinition = "jsonb")
    String contactData;

    @ManyToOne
    @JoinColumn(name = "book_ref", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "ticket_no", insertable = false, updatable = false)
    private TicketFlight ticketFlight;

}
