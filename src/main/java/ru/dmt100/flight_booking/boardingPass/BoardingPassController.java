package ru.dmt100.flight_booking.boardingPass;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmt100.flight_booking.boardingPass.model.BoardingPass;
import ru.dmt100.flight_booking.boardingPass.model.dto.BoardingPassDto;
import ru.dmt100.flight_booking.dao.Dao;
import ru.dmt100.flight_booking.util.ResponseUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.dmt100.flight_booking.constant.Constant.USER_ID;
import static ru.dmt100.flight_booking.constant.Constant.X_PROCESSING_TIME;

@RestController
@RequestMapping("/boarding-pass")
public class BoardingPassController {
    private final Dao boardingPassDao;

    public BoardingPassController(@Qualifier("boardingPassDaoImpl")Dao boardingPassDao) {
        this.boardingPassDao = boardingPassDao;
    }
//    private final BoardingPassService boardingPassService;

//    public BoardingPassController(@Qualifier("boardingPassDaoImpl") Dao boardingPassDao,
//                                  @Qualifier("boardingPassServiceImpl") BoardingPassService boardingPassService) {
//        this.boardingPassDao = boardingPassDao;
//        this.boardingPassService = boardingPassService;
//    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Optional<BoardingPass>> save(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody BoardingPass boardingPass) {
        double timeStart = System.currentTimeMillis();

        Optional<BoardingPass> savedBoardingPass = boardingPassDao.save(userId, boardingPass);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(savedBoardingPass);
    }

    @GetMapping("/{ticketNo}")
    public ResponseEntity<Optional<BoardingPass>> find(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String ticketNo) {
        double timeStart = System.currentTimeMillis();

        Optional<BoardingPass> boardingPass = boardingPassDao.find(userId, ticketNo);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(boardingPass);
    }

    @GetMapping("/limit/{limit}")
    public ResponseEntity<?> findAll(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable("limit") Integer limit) {
        double timeStart = System.currentTimeMillis();

        List<BoardingPass> boardingPasses = boardingPassDao.findAll(userId, limit);

        return ResponseUtil.headersMaker(timeStart, boardingPasses);
    }

    @PatchMapping()
    public ResponseEntity<Optional<BoardingPassDto>> update(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody BoardingPass boardingPass) {
        double timeStart = System.currentTimeMillis();

        Optional<BoardingPassDto> updatedBoardingPass = boardingPassDao.update(userId, boardingPass);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body(updatedBoardingPass);
    }

    @DeleteMapping("/{ticketNo}")
    public ResponseEntity<?> delete(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @PathVariable String ticketNo) {
        double timeStart = System.currentTimeMillis();

        boolean isBoardingPassDeleted = boardingPassDao.delete(userId, ticketNo);

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body("Is boarding pass deleted: " + isBoardingPassDeleted);
    }

    @DeleteMapping("/boarding-passes-delete")
    public ResponseEntity<?> deleteListOfBoardingPasses(
            @RequestHeader(value = USER_ID, required = false) Long userId,
            @RequestBody Map<String, List<String>> boardingPassDeleteRequest) {
        double timeStart = System.currentTimeMillis();

        boolean areBoardingPassesDeleted = boardingPassDao.deleteList(userId,
                boardingPassDeleteRequest.get("ticketNosList"));

        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_PROCESSING_TIME, qTime + " sec.");

        return ResponseEntity.ok().headers(headers).body("Are boarding passes deleted: " + areBoardingPassesDeleted);
    }
}
