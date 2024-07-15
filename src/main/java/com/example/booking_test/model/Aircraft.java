package com.example.booking_test.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.Positive;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "aircrafts_data")
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    @Column(name = "aircraft_code", length = 3)
    String aircraftCode;

    @NonNull
    @Column(name = "model", nullable = false, columnDefinition = "jsonb")
    String model;

    @NonNull
    @Column(name = "range", nullable = false)
    @Positive
    int range;

}
