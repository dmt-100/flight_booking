package com.example.booking_test.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Column(name = "ticket_no", nullable = false)
    Long ticketNumber;

    @NonNull
    @Column(name = "book_ref", nullable = false, length = 6)
    @OneToOne
    Booking bookRef;

    @NonNull
    @Column(name = "passenger_id", nullable = false, length = 20)
    String passengerId;

    @NonNull
    @Column(name = "passenger_name", nullable = false, length = 100)
    String passengerName;

    @Column(name = "contact_data", columnDefinition = "jsonb")
    String contactData;

//    @OneToMany(mappedBy = "tickets", cascade = CascadeType.ALL)
//    List<TicketFlight> ticketFlights;

}
