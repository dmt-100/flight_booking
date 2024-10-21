package ru.dmt100.flight_booking.entity.ticket.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.dmt100.flight_booking.entity.booking.model.Booking;
import ru.dmt100.flight_booking.util.MapConverter;

import java.util.Map;

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

    @Column(name = "passenger_id", nullable = false)
    Long passengerId;

    @Column(name = "passenger_name", nullable = false)
    String passengerName;

    @Column(name = "contact_data", columnDefinition = "jsonb")
    @Convert(converter = MapConverter.class)
    Map<String, String> contactData;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_ref")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Booking booking;

    public Ticket(Long ticketNo,
                  String bookRef,
                  Long passengerId,
                  String passengerName,
                  Map<String, String> contactData) {
        this.ticketNo = ticketNo;
        this.bookRef = bookRef;
        this.passengerId = passengerId;
        this.passengerName = passengerName;
        this.contactData = contactData;
    }
}
