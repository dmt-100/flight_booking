package ru.dmt100.flight_booking.ticket;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmt100.flight_booking.dao.Dao;
import ru.dmt100.flight_booking.ticket.dto.TicketLiteDtoResponse;
import ru.dmt100.flight_booking.ticket.model.Ticket;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.dmt100.flight_booking.constant.Constant.USER_ID;
import static ru.dmt100.flight_booking.constant.Constant.X_PROCESSING_TIME;

@RestController
@AllArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    @Qualifier("ticketDaoImpl")
    private final Dao<Long, String, Ticket, TicketLiteDtoResponse> ticketDao;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Optional<TicketLiteDtoResponse>> save(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody Ticket ticket) {
        double timeStart = System.currentTimeMillis();

        Optional<TicketLiteDtoResponse> ticketLiteDtoResponse =
                (Optional<TicketLiteDtoResponse>) ticketDao.save(userId, ticket);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(ticketLiteDtoResponse);
    }

    @GetMapping("/{ticketNo}")
    public ResponseEntity<Optional<TicketLiteDtoResponse>> get(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String ticketNo) {
        double timeStart = System.currentTimeMillis();

        Optional<TicketLiteDtoResponse> ticketDtoResponse =
                (Optional<TicketLiteDtoResponse>) ticketDao.find(userId, ticketNo);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(ticketDtoResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<TicketLiteDtoResponse>> getAllTickets(
            @RequestHeader(value = USER_ID, required = false) Long userId) {
        double timeStart = System.currentTimeMillis();

        List<TicketLiteDtoResponse> ticketsDtoResponse = ticketDao.findAll(userId);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(ticketsDtoResponse);
    }

    @PatchMapping()
    public ResponseEntity<Optional<TicketLiteDtoResponse>> update(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody Ticket ticket) {
        double timeStart = System.currentTimeMillis();

        Optional<TicketLiteDtoResponse> ticketsDtoResponse =
                (Optional<TicketLiteDtoResponse>) ticketDao.update(userId, ticket);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(ticketsDtoResponse);
    }

    @DeleteMapping("/{ticketNo}")
    public ResponseEntity<?> delete(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String ticketNo) {
        double timeStart = System.currentTimeMillis();

        boolean isTicketDeleted = ticketDao.delete(userId, ticketNo);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body("Is ticket deleted: " + isTicketDeleted);
    }

    @DeleteMapping("/tickets-delete")
    public ResponseEntity<?> deleteListOfTickets(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody Map<String, List<String>> ticketDeleteRequest) {
        double timeStart = System.currentTimeMillis();

        boolean areTicketDeleted = ticketDao.deleteList(userId, ticketDeleteRequest.get("ticketNos"));

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body("Are ticket deleted: " + areTicketDeleted);

    }

}
