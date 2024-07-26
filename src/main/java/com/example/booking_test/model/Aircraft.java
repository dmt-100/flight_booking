package com.example.booking_test.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.Positive;
import java.util.List;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    @NonNull
    @Column(name = "aircraft_code", nullable = false, length = 3)
    String aircraftCode;

    @NonNull
    @Column(name = "model", nullable = false, columnDefinition = "jsonb")
    String model;

    @NonNull
    @Column(name = "range", nullable = false)
    @Positive
    Integer  range;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Seat> seats;

}
