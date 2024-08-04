package ru.dmt100.flight_booking.ticket.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.dmt100.flight_booking.booking.model.Booking;
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
    String ticketNo;

    @Column(name = "book_ref", insertable=false, updatable=false, nullable = false)
    String bookRef;

    @Column(name = "passenger_id", nullable = false)
    Long passengerId;

    @Column(name = "passenger_name", nullable = false)
    String passengerName;

    @Column(name = "contact_data", columnDefinition = "jsonb")
    @Convert(converter = MapConverter.class)
//    @JsonProperty("contact_data")
    Map<String, String> contactData;

    @ManyToOne
    @JoinColumn(name = "book_ref", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Booking booking;

}
