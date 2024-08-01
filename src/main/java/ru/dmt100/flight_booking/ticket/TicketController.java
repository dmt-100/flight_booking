package ru.dmt100.flight_booking.ticket;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmt100.flight_booking.ticket.model.Ticket;
import ru.dmt100.flight_booking.ticket.service.TicketService;
import ru.dmt100.flight_booking.ticket.dto.TicketDtoResponse;

import static ru.dmt100.flight_booking.constant.Constant.USER_ID;

@RestController
@AllArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createBooking(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody Ticket ticket) {
        ticketService.save(userId, ticket);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{ticketNo}")
    public ResponseEntity<TicketDtoResponse> getTicketById(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable Long ticketNo) {
        TicketDtoResponse ticketDtoResponse = ticketService.findTicketById(userId, ticketNo);
        return ResponseEntity.ok(ticketDtoResponse);
    }
}
