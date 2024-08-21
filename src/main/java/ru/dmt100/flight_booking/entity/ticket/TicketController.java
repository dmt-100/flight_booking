//package ru.dmt100.flight_booking.entity.ticket;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ru.dmt100.flight_booking.dao.Dao;
//import ru.dmt100.flight_booking.entity.ticket.model.dto.TicketDto;
//import ru.dmt100.flight_booking.entity.ticket.model.dto.record.PassengersInfoByFlightId;
//import ru.dmt100.flight_booking.entity.ticket.model.dto.record.TicketOnScheduledFlightsByTimeRange;
//import ru.dmt100.flight_booking.entity.ticket.model.Ticket;
//import ru.dmt100.flight_booking.entity.ticket.service.TicketService;
//import ru.dmt100.flight_booking.util.HeadersMaker;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import static ru.dmt100.flight_booking.constant.Constant.USER_ID;
//import static ru.dmt100.flight_booking.constant.Constant.X_PROCESSING_TIME;
//
//@RestController
//@RequestMapping("/ticket")
//public class TicketController {
//    private final Dao ticketDao;
//    private final TicketService ticketService;
//
//    public TicketController(@Qualifier("ticketDaoImpl")Dao ticketDao,
//                            @Qualifier("ticketServiceImpl")TicketService ticketService) {
//        this.ticketDao = ticketDao;
//        this.ticketService = ticketService;
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<Optional<TicketDto>> save(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @RequestBody Ticket ticket) {
//        double timeStart = System.currentTimeMillis();
//
//        Optional<TicketDto> ticketLiteDtoResponse = ticketDao.save(userId, ticket);
//
//        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(X_PROCESSING_TIME, qTime + " sec.");
//
//        return ResponseEntity.ok().headers(headers).body(ticketLiteDtoResponse);
//    }
//
//    @GetMapping("/{ticketNo}")
//    public ResponseEntity<Optional<TicketDto>> find(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @PathVariable String ticketNo) {
//        double timeStart = System.currentTimeMillis();
//
//        Optional<TicketDto> ticketDtoResponse = ticketDao.find(userId, ticketNo);
//
//        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(X_PROCESSING_TIME, qTime + " sec.");
//
//        return ResponseEntity.ok().headers(headers).body(ticketDtoResponse);
//    }
//
//    @GetMapping("")
//    public ResponseEntity<?> findAll(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @RequestParam(value = "limit", required = false) Integer limit) {
//        double timeStart = System.currentTimeMillis();
//
//        List<TicketDto> tickets = ticketDao.findAll(userId, limit);
//
//        return HeadersMaker.make(timeStart, tickets);
//    }
//
//    @PatchMapping()
//    public ResponseEntity<Optional<TicketDto>> update(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @RequestBody Ticket ticket) {
//        double timeStart = System.currentTimeMillis();
//
//        Optional<TicketDto> ticketsDtoResponse = ticketDao.update(userId, ticket);
//
//        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(X_PROCESSING_TIME, qTime + " sec.");
//
//        return ResponseEntity.ok().headers(headers).body(ticketsDtoResponse);
//    }
//
//    @DeleteMapping("/{ticketNo}")
//    public ResponseEntity<?> delete(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @PathVariable String ticketNo) {
//        double timeStart = System.currentTimeMillis();
//
//        boolean isTicketDeleted = ticketDao.delete(userId, ticketNo);
//
//        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(X_PROCESSING_TIME, qTime + " sec.");
//
//        return ResponseEntity.ok().headers(headers).body("Is ticket deleted: " + isTicketDeleted);
//    }
//
//
//    @DeleteMapping("/tickets-delete")
//    public ResponseEntity<?> deleteListOfTickets(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @RequestBody Map<String, List<String>> ticketDeleteRequest) {
//        double timeStart = System.currentTimeMillis();
//
//        boolean areTicketsDeleted = ticketDao.deleteList(userId, ticketDeleteRequest.get("ticketNos"));
//
//        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(X_PROCESSING_TIME, qTime + " sec.");
//
//        return ResponseEntity.ok().headers(headers).body("Are tickets deleted: " + areTicketsDeleted);
//
//    }
//
//
//
//
//    @GetMapping("/passengerInfo")
//    public ResponseEntity<?> getPassengerInfoByListOfTicketNos(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @RequestBody Map<String, List<String>> passengerTicketNo) {
//        double timeStart = System.currentTimeMillis();
//
//        List<Optional<TicketDto>> passengerInfos =
//                ticketService.findPassengersInfoByListOfTicketNos(userId,
//                        passengerTicketNo.get("passengerTicketNo"));
//
//        return HeadersMaker.make(timeStart, passengerInfos);
//    }
//
//    @GetMapping("/passengerInfo/{flightId}")
//    public ResponseEntity<?> getPassengerInfoByFlight(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @PathVariable Long flightId) {
//        double timeStart = System.currentTimeMillis();
//
//        List<PassengersInfoByFlightId> passengerInfos =
//                ticketService.findPassengersInfoByFlightId(userId, flightId);
//
//        return HeadersMaker.make(timeStart, passengerInfos);
//    }
//
//    @GetMapping("/countTickets")
//    public ResponseEntity<?> getCountTicketsOnScheduledFlightsByTimeRange(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @RequestParam LocalDateTime startDate,
//            @RequestParam LocalDateTime endDate) {
//        double timeStart = System.currentTimeMillis();
//
//        List<TicketOnScheduledFlightsByTimeRange> passengerInfos =
//                ticketService.getCountTicketsOnScheduledFlightsByTimeRange(userId, startDate, endDate);
//
//        return HeadersMaker.make(timeStart, passengerInfos);
//    }
//}
