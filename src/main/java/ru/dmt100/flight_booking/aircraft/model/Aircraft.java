package ru.dmt100.flight_booking.aircraft.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dmt100.flight_booking.seat.model.Seat;
import ru.dmt100.flight_booking.util.MapConverter;

import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "aircrafts_data")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Aircraft {

    @Id
    @Column(name = "aircraft_code", nullable = false, length = 3)
    String aircraftCode;

    @Column(name = "model", nullable = false, columnDefinition = "jsonb")
    @Convert(converter = MapConverter.class)
    Map<String, String> model;

    @Column(name = "range", nullable = false)
    Integer  range;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Seat> seats;

}
