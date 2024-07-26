package com.example.booking_test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;;

    @NonNull
    @Column(name = "book_ref", length = 6, nullable = false)
    private String bookRef;

    @NonNull
    @Column(name = "book_date", nullable = false)
    private ZonedDateTime bookDate;

    @NonNull
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> tickets;

//    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Ticket> tickets;
}
