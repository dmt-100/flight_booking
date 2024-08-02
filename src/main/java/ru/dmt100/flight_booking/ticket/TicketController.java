package ru.dmt100.flight_booking.ticket;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;
import ru.dmt100.flight_booking.ticket.model.Ticket;
import ru.dmt100.flight_booking.ticket.service.TicketService;

import static ru.dmt100.flight_booking.constant.Constant.USER_ID;
import static ru.dmt100.flight_booking.constant.Constant.X_PROCESSING_TIME;

@RestController
@AllArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TicketLiteDtoResponse> saveTicket(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody Ticket ticket) {
        long timeStart = System.currentTimeMillis();
        TicketLiteDtoResponse ticketLiteDtoResponse = ticketService.save(userId, ticket);

        double queryTime = (System.currentTimeMillis() - timeStart) / 1000.0;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, queryTime + " sec.");
        return ResponseEntity.ok().headers(headers).body(ticketLiteDtoResponse);
    }

    @GetMapping("/{ticketNo}")
    public ResponseEntity<TicketLiteDtoResponse> getTicketById(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String ticketNo) {
        long timeStart = System.currentTimeMillis();
        TicketLiteDtoResponse ticketDtoResponse = ticketService.getTicketLiteDtoResponse(userId, ticketNo);

        double queryTime = (System.currentTimeMillis() - timeStart) / 1000.0;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, queryTime + " sec.");
        return ResponseEntity.ok().headers(headers).body(ticketDtoResponse);
    }
}
