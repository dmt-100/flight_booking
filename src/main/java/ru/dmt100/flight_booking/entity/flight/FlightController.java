//package ru.dmt100.flight_booking.entity.flight;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ru.dmt100.flight_booking.dao.Dao;
//import ru.dmt100.flight_booking.entity.flight.dto.FlightDto;
//import ru.dmt100.flight_booking.entity.flight.dto.record.*;
//import ru.dmt100.flight_booking.entity.flight.model.Flight;
//import ru.dmt100.flight_booking.entity.flight.service.FlightService;
//import ru.dmt100.flight_booking.util.HeadersMaker;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import static ru.dmt100.flight_booking.constant.Constant.*;
//
//@RestController
//@RequestMapping("/flight")
//public class FlightController {
//    private final Dao flightDao;
//    private final FlightService flightService;
//    public FlightController(@Qualifier("flightDaoImpl")Dao flightDao,
//                            @Qualifier("flightServiceImpl") FlightService flightService) {
//        this.flightDao = flightDao;
//        this.flightService = flightService;
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<Optional<FlightDto>> save(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @RequestBody Flight flight) {
//        double timeStart = System.currentTimeMillis();
//
//        Optional<FlightDto> flightDtoResponse = (Optional<FlightDto>) flightDao.save(userId, flight);
//
//        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(X_PROCESSING_TIME, qTime + " sec.");
//
//        return ResponseEntity.ok().headers(headers).body(flightDtoResponse);
//    }
//
//    @GetMapping("/{flightId}")
//    public ResponseEntity<Optional<FlightDto>> find(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @PathVariable String flightId) {
//        double timeStart = System.currentTimeMillis();
//
//        Optional<FlightDto> flightDtoResponse =
//                (Optional<FlightDto>) flightDao.find(userId, flightId);
//
//        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(X_PROCESSING_TIME, qTime + " sec.");
//
//        return ResponseEntity.ok().headers(headers).body(flightDtoResponse);
//    }
//
//    @PatchMapping()
//    public ResponseEntity<Optional<FlightDto>> update(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @RequestBody FlightDto flight) {
//        double timeStart = System.currentTimeMillis();
//
//        Optional<FlightDto> flightDtoResponse =
//                (Optional<FlightDto>) flightDao.update(userId, flight);
//
//        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(X_PROCESSING_TIME, qTime + " sec.");
//
//        return ResponseEntity.ok().headers(headers).body(flightDtoResponse);
//    }
//
//    @DeleteMapping("/{flightId}")
//    public ResponseEntity<?> delete(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @PathVariable Long flightId) {
//        double timeStart = System.currentTimeMillis();
//
//        boolean isFlightDeleted = flightDao.delete(userId, flightId);
//
//        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(X_PROCESSING_TIME, qTime + " sec.");
//
//        return ResponseEntity.ok().headers(headers).body("Is flight deleted: " + isFlightDeleted);
//    }
//
//    @DeleteMapping("/tickets-delete")
//    public ResponseEntity<?> deleteListOfFlights(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @RequestBody Map<String, List<Long>> flightIds) {
//        double timeStart = System.currentTimeMillis();
//
//        boolean areFlightsDeleted = flightDao.deleteList(userId, flightIds.get("flightIds"));
//
//        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(X_PROCESSING_TIME, qTime + " sec.");
//
//        return ResponseEntity.ok().headers(headers).body("Are flights deleted: " + areFlightsDeleted);
//
//    }
//
//
//    //Service
//    @GetMapping("/all-tickets/{flightId}")
//    public ResponseEntity<List<String>> findAllTicketsByFlightId(
//            @RequestHeader(value = USER_ID, required = false) Long userId,
//            @PathVariable Long flightId) {
//        double timeStart = System.currentTimeMillis();
//
//        List<String> tickets = flightService.findAllTicketsByFlightId(userId, flightId);
//
//        return headersMaker(timeStart, tickets);
//    }
//
//    // Statistics queries
//    @GetMapping("/stats/flightCountByStatus")
//    public ResponseEntity<List<FlightCountByStatus>> getFlightCountByStatus() {
//        double timeStart = System.currentTimeMillis();
//
//        List<FlightCountByStatus> stats = flightService.getFlightCountByStatus();
//
//        return headersMaker(timeStart, stats);
//    }
//
//
//    @GetMapping("/stats/flightCountByMonth")
//    public ResponseEntity<List<FlightsCountByMonth>> getFlightsCountByMonth() {
//        double timeStart = System.currentTimeMillis();
//
//        List<FlightsCountByMonth> stats = flightService.getFlightsCountByMonth();
//
//        return headersMaker(timeStart, stats);
//    }
//
//    @GetMapping("/stats/delayedFlightsWithPassengers")
//    public ResponseEntity<List<DelayedFlightsWithPassengers>> getDelayedFlightsWithPassengers() {
//        double timeStart = System.currentTimeMillis();
//
//        List<DelayedFlightsWithPassengers> stats = flightService.getDelayedFlightsWithPassengers();
//
//        return headersMaker(timeStart, stats);
//    }
//
//    @GetMapping("/stats/flightsDelayedMoreThanTwoHours")
//    public ResponseEntity<List<FlightsDelayedMoreThanTwoHours>> getFlightsDelayedMoreThanTwoHours() {
//        double timeStart = System.currentTimeMillis();
//
//        List<FlightsDelayedMoreThanTwoHours> stats = flightService.getFlightsDelayedMoreThanTwoHours();
//
//        return headersMaker(timeStart, stats);
//    }
//
//    @GetMapping("/stats/mostPopularRoutes")
//    public ResponseEntity<List<MostPopularRoutes>> getMostPopularRoutes() {
//        double timeStart = System.currentTimeMillis();
//
//        List<MostPopularRoutes> stats = flightService.getMostPopularRoutes();
//
//        return headersMaker(timeStart, stats);
//    }
//
//    @GetMapping("/stats/avgDelayByDayOfWeek")
//    public ResponseEntity<?> getAvgDelayByDayOfWeek() {
//        double timeStart = System.currentTimeMillis();
//
//        List<AvgDelayByDayOfWeek> stats = flightService.getAvgDelayByDayOfWeek();
//
//        return HeadersMaker.make(timeStart, stats);
//    }
//    private ResponseEntity headersMaker(double timeStart, List<?> list) {
//        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(X_PROCESSING_TIME, qTime + " sec.");
//        headers.add(X_TOTAL_RECORDS, String.valueOf(list.size()));
//
//        return ResponseEntity.ok().headers(headers).body(list);
//    }
//
//}
