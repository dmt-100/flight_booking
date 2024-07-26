package com.example.booking_test.model;

import com.example.booking_test.model.enums.FareConditions;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "seats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Column(name = "aircraft_code", nullable = false, length = 3)
    String aircraftCode;

    @NonNull
    @Column(name = "seat_no", nullable = false, length = 4)
    String seatNo;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fare_conditions", nullable = false)
    FareConditions fareConditions;

    @ManyToOne
    @JoinColumn(name = "aircraft_code", insertable = false, updatable = false)
    Aircraft aircraft;

}
