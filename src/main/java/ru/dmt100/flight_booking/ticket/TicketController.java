package ru.dmt100.flight_booking.ticket;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;
import ru.dmt100.flight_booking.ticket.model.Ticket;
import ru.dmt100.flight_booking.ticket.service.TicketService;

import java.util.List;

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
        double timeStart = System.currentTimeMillis();

        TicketLiteDtoResponse ticketLiteDtoResponse = ticketService.save(userId, ticket);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(ticketLiteDtoResponse);
    }

    @GetMapping("/{ticketNo}")
    public ResponseEntity<TicketLiteDtoResponse> getTicketById(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String ticketNo) {
        double timeStart = System.currentTimeMillis();

        TicketLiteDtoResponse ticketDtoResponse = ticketService.getTicketLiteDtoResponse(userId, ticketNo);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(ticketDtoResponse);
    }

    @GetMapping("")
    public ResponseEntity<List <TicketLiteDtoResponse>> getAllTickets(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        double timeStart = System.currentTimeMillis();

        List<TicketLiteDtoResponse> ticketsDtoResponse = ticketService.findAllTicketsLite(userId);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(ticketsDtoResponse);
    }


}
