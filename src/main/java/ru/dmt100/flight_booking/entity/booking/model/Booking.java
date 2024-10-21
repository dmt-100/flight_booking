package ru.dmt100.flight_booking.entity.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dmt100.flight_booking.entity.ticket.model.Ticket;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "bookings")
@Builder
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@JsonIgnoreProperties({"tickets"})
public class Booking {
    @Id
    @Column(name = "book_ref", length = 6, nullable = false)
    String bookRef;
    @Column(name = "book_date", nullable = false)
    OffsetDateTime bookDate;
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    BigDecimal totalAmount;
    @OneToMany(mappedBy = "booking",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    Set<Ticket> tickets;

    public Booking(String bookRef, OffsetDateTime bookDate, BigDecimal totalAmount, Set<Ticket> tickets) {
        this.bookRef = bookRef;
        this.bookDate = bookDate;
        this.totalAmount = totalAmount;
        this.tickets = tickets;
    }

    public Booking(String bookRef, OffsetDateTime bookDate, BigDecimal totalAmount) {
        this.bookRef = bookRef;
        this.bookDate = bookDate;
        this.totalAmount = totalAmount;
    }
}
