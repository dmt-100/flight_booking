package ru.dmt100.flight_booking.boardingPass.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "boarding_passes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"flight_id", "seat_no"})
        })
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class BoardingPass {

    @Id
    @Column(name = "ticket_no", insertable = false, updatable = false, nullable = false)
    String ticketNo;

    @Column(name = "flight_id", nullable = false)
    Long flightId;

    @Column(name = "boarding_no", nullable = false)
    Short boardingNo;

    @Column(name = "seat_no", nullable = false, length = 4)
    String seatNo;

}
