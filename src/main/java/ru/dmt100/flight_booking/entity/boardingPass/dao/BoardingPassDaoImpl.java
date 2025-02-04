//package ru.dmt100.flight_booking.entity.boardingPass.dao;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.dmt100.flight_booking.dao.Dao;
//import ru.dmt100.flight_booking.entity.boardingPass.model.BoardingPass;
//import ru.dmt100.flight_booking.entity.boardingPass.model.dto.BoardingPassDto;
//import ru.dmt100.flight_booking.exception.NotFoundException;
//import ru.dmt100.flight_booking.sql.SqlQuery;
//import ru.dmt100.flight_booking.util.ConnectionManager;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Service("boardingPassDaoImpl")
//@AllArgsConstructor
//@Transactional(readOnly = true)
//public class BoardingPassDaoImpl implements Dao<Long, String, Integer, BoardingPass> {
//    private final SqlQuery sqlQuery = new SqlQuery();
//
//    //    private final Validator validator = new Validator(sqlQuery);
//    @Override
//    public Optional<BoardingPass> save(Long userId, BoardingPass boardingPass) {
//        var ticketNo = boardingPass.getTicketNo();
//        var flightId = boardingPass.getFlightId();
//
//        Optional<BoardingPass> optionalBoardingPassDto = null;
////
////        try (var con = ConnectionManager.open()) {
////            if (!validator.checkBoardingPass(con, ticketNo, flightId)) {
////                throw new RuntimeException("BoardingPass with ticket number " + ticketNo + " and flight ID " + flightId + " does not exist.");
////            }
////            var boardingNo = boardingPass.getBoardingNo();
////            var seatNo = boardingPass.getSeatNo();
////
////            try (var stmt = con.prepareStatement(sqlQuery.getNEW_BOARDING_PASS())) {
////                stmt.setString(1, ticketNo);
////                stmt.setLong(2, flightId);
////                stmt.setLong(3, boardingNo);
////                stmt.setString(4, seatNo);
////
////                var row = stmt.executeUpdate();
////
////                if (row == 0) {
////                    throw new SaveException("Failed to save a boarding pass.");
////                }
////            }
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////        return find(userId, ticketNo);
//
//
//        return optionalBoardingPassDto;
//    }
//
//    @Override
//    public Optional<BoardingPass> find(Long userId, String ticketNo) {
//        Optional<BoardingPass> boardingPass = null;
//        try (var con = ConnectionManager.open()) {
//            if (validator.checkBoardingPass(con, ticketNo)) {
//                throw new NotFoundException("Boarding Pass " + ticketNo + ", does not exist");
//            }
//            boardingPass = fetch(con, ticketNo);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return boardingPass;
//    }
//
//    @Override
//    public List<BoardingPass> findAll(Long aLong, Integer integer) {
//        return null;
//    }
//
//
//    //    @Override
////    public List<Optional<BoardingPassDto>> findAll(Long userId, Integer limit) {
////        List<Optional<BoardingPassDto>> boardingPasses = new ArrayList<>();
////
////        try (var con = ConnectionManager.open();
////             var stmt = con.prepareStatement(sqlQuery.getALL_BOARDING_PASSES())) {
////            stmt.setInt(1, limit);
////            var rs = stmt.executeQuery();
////            while (rs.next()) {
////                String ticketNo = rs.getString("ticket_no");
////
////                Optional<BoardingPassDto> boardingPass = fetch(con, ticketNo);
////                boardingPasses.add(boardingPass);
////            }
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////        return boardingPasses;
////    }
//    @Override
//    public Optional<BoardingPass> update(Long aLong, BoardingPass boardingPass) {
//        return Optional.empty();
//    }
//
////    @Override
////    public Optional<BoardingPassDto> update(Long userId, BoardingPass boardingPass) {
////        Optional<BoardingPassDto> boardingPassDto = null;
////        return boardingPassDto;
//////        var ticketNo = boardingPass.getTicketNo();
//////        var flightId = boardingPass.getFlightId();
//////        var boardingNo = boardingPass.getBoardingNo();
//////        var seatNo = boardingPass.getSeatNo();
//////
//////        try (var con = ConnectionManager.open()) {
//////            if (validator.checkBoardingPass(con, ticketNo)) {
//////                throw new NotFoundException("Boarding Pass with ticket number: " + ticketNo + ", does not exist");
//////            }
//////
//////            // Start the transaction
//////            con.setAutoCommit(false);
//////
//////            try {
//////                // Check if the combination of flight_id and seat_no already exists
//////                try (var checkStmt = con.prepareStatement(
//////                        "SELECT 1 FROM boarding_passes WHERE ticket_no = ? and flight_id = ? AND seat_no = ?")) {
//////                    checkStmt.setString(1, ticketNo);
//////                    checkStmt.setLong(2, flightId);
//////                    checkStmt.setString(3, seatNo);
//////                    try (var rs = checkStmt.executeQuery()) {
//////                        if (rs.next()) {
//////                            throw new UpdateException("Seat " + seatNo + " on flight " + flightId + " is already occupied.");
//////                        }
//////                    }
//////                }
//////
//////                // Delete the current record
//////                try (var deleteStmt = con.prepareStatement("DELETE FROM boarding_passes WHERE ticket_no = ?")) {
//////                    deleteStmt.setString(1, ticketNo);
//////                    deleteStmt.executeUpdate();
//////                }
//////
//////                // Add the new record
//////                try (var insertStmt = con.prepareStatement(sqlQuery.getNEW_BOARDING_PASS())) {
//////                    insertStmt.setString(1, ticketNo);
//////                    insertStmt.setLong(2, flightId);
//////                    insertStmt.setInt(3, boardingNo);
//////                    insertStmt.setString(4, seatNo);
//////
//////                    var row = insertStmt.executeUpdate();
//////                    if (row == 0) {
//////                        throw new UpdateException("Failed to update the boarding pass: " + ticketNo);
//////                    }
//////                }
//////
//////                // Commit the transaction
//////                con.commit();
//////
//////                return fetch(con, ticketNo);
//////            } catch (SQLException e) {
//////                // Roll back the transaction in case of an error
//////                con.rollback();
//////                throw new RuntimeException(e);
//////            } finally {
//////                // Return auto-commit to its original state
//////                con.setAutoCommit(true);
//////            }
//////
//////        } catch (SQLException e) {
//////            throw new RuntimeException(e);
//////        }
////    }
//
//    @Override
//    public boolean delete(Long userId, String ticketNoflightId) {
////        String[] parts = ticketNoflightId.split("_");
////        String ticketNoKey = parts[0];
////        Long flightIdKey = Long.parseLong(parts[1]);
////
////        try (var con = ConnectionManager.open()) {
////            if (!validator.checkBoardingPass(con, ticketNoKey, flightIdKey)) {
////                throw new NotFoundException(
////                        "Boarding Pass with composite key: " + ticketNoKey + "_" +flightIdKey +", does not exist");
////            }
////            try (var stmt = con.prepareStatement(sqlQuery.getDELETE_BOARDING_PASS())) {
////                stmt.setString(1, ticketNoKey);
////                stmt.setLong(2, flightIdKey);
////                var rs = stmt.executeUpdate();
////                if (rs == 0) {
////                    throw new DeleteException(
////                            "Failed to delete boarding pass with composite key: " + ticketNoKey + "_" +flightIdKey);
////                }
////            }
////            if (!validator.checkBoardingPass(con, ticketNoKey, flightIdKey)) {
////                return true;
////            }
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
//        return false;
//    }
//
//    @Override
//    public boolean deleteList(Long userId, List<String> compositeKeys) {
//        if (compositeKeys == null || compositeKeys.isEmpty()) {
//            return false;
//        }
//
//        for (String compositeKey : compositeKeys) {
//            try {
//                delete(userId, compositeKey);
//            } catch (Exception e) {
//                // Log the error or handle the exception
//                e.printStackTrace();
//                return false; // or continue processing the remaining items
//            }
//        }
//        return true;
//    }
//
//
//    private Optional<BoardingPassDto> fetch(Connection con, String ticketNo) {
//        Optional<BoardingPassDto> boardingPassDto = null;
//        return boardingPassDto;
//
////        try (var stmt = con.prepareStatement(sqlQuery.getBOARDING_PASS_BY_BOARDING_NO())) {
////            stmt.setString(1, ticketNo);
////            var rs = stmt.executeQuery();
////            if (!rs.next()) {
////                throw new NotFoundException("BoardingPass " + ticketNo + ", does not exist");
////            } else {
////
////                var flightId = rs.getLong("flight_id");
////                var boardingNo = rs.getShort("boarding_no");
////                var seatNo = rs.getString("seat_no");
////
////                return Optional.of(new BoardingPassDto(ticketNo, flightId, boardingNo, seatNo));
////            }
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
//    }
//
//
//}
//
