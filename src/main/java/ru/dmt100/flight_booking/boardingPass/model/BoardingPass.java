package ru.dmt100.flight_booking.boardingPass.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dmt100.flight_booking.flight.model.Flight;

@Entity
@Table(name = "boarding_passes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class BoardingPass {

    @Id
    @JoinColumn(name = "ticket_no", insertable = false, updatable = false)
    String ticketNo;

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "flight_id", insertable = false, updatable = false)
    Flight flight;

    @Column(name = "boarding_no", nullable = false)
    Integer boardingNo;

    @Column(name = "seat_no", nullable = false, length = 4)
    String seatNo;

}
