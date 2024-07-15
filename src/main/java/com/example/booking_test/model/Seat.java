package com.example.booking_test.model;

import com.example.booking_test.model.enums.FareConditions;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "aircraft_code")
    Aircraft aircraftCode;

    @NonNull
    @Column(name = "seat_no", nullable = false, length = 4)
    String seatId;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fare_conditions", nullable = false)
    FareConditions fareConditions;

}
