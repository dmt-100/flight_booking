package ru.dmt100.bookingflight.seat.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dmt100.bookingflight.aircraft.model.Aircraft;
import ru.dmt100.bookingflight.enums.FareConditions;

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
    @Column(name = "aircraft_code", nullable = false, length = 3)
    String aircraftCode;

    @Column(name = "seat_no", nullable = false, length = 4)
    String seatNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "fare_conditions", nullable = false)
    FareConditions fareConditions;

    @ManyToOne
    @JoinColumn(name = "aircraft_code", insertable = false, updatable = false)
    Aircraft aircraft;

}
