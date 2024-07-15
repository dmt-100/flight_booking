package com.example.booking_test.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.hibernate.type.YesNoConverter;

import java.awt.*;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "airports_data")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_code", length = 3)
    String code;

    @Column(name = "airport_name", nullable = false, columnDefinition = "jsonb")
    @NonNull
    String name;

    @Column(name = "city", nullable = false)
    @NonNull
    String city;

    @Column(name = "coordinates", nullable = false)
    @NonNull
    Point coordinates;

    @Column(name = "timezone", nullable = false)
    @NonNull
    String timezone;


}
