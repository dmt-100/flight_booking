package ru.dmt100.flight_booking.booking.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dmt100.flight_booking.ticket.model.Ticket;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "bookings")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Booking {

    @Id
    @Column(name = "book_ref", length = 6, nullable = false)
    private String bookRef;

    @Column(name = "book_date", nullable = false)
    private ZonedDateTime bookDate;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "booking",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<Ticket> tickets;

    public Booking(String bRef, ZonedDateTime dateTIme, BigDecimal totalAmount) {
    }

}
