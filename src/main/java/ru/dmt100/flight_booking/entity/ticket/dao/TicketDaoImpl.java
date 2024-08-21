//package ru.dmt100.flight_booking.entity.ticket.dao;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.dmt100.flight_booking.entity.boardingPass.sql.query.BoardingPassSqlQuery;
//import ru.dmt100.flight_booking.dao.Dao;
//import ru.dmt100.flight_booking.entity.ticket.model.dto.TicketDto;
//import ru.dmt100.flight_booking.entity.ticket.sql.query.TicketSqlQuery;
//import ru.dmt100.flight_booking.exception.*;
//import ru.dmt100.flight_booking.entity.ticket.model.Ticket;
//import ru.dmt100.flight_booking.util.ConnectionManager;
//import ru.dmt100.flight_booking.util.MapConverter;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.IntStream;
//
//@Slf4j
//@Service("ticketDaoImpl")
//@Transactional(readOnly = true)
//public class TicketDaoImpl implements Dao<Long, String, Integer, TicketDto, Ticket> {
//    TicketSqlQuery ticketSqlQuery;
//    BoardingPassSqlQuery boardingPassSqlQuery;
//
//    public TicketDaoImpl(@Qualifier("ticketSqlQuery") TicketSqlQuery ticketSqlQuery,
//                         @Qualifier("boardingPassSqlQuery") BoardingPassSqlQuery boardingPassSqlQuery) {
//        this.ticketSqlQuery = ticketSqlQuery;
//        this.boardingPassSqlQuery = boardingPassSqlQuery;
//    }
//
//    @Override
//    public Optional<Ticket> save(Long userId, TicketDto ticketDto) {
//        Optional<Ticket> ticket = null;
////        var ticketNo = String.format("%013d", ticket.getTicketNo());  // Добавляем три нуля впереди
////        var bookRef = ticket.getBookRef();
////        var passengerId = formatPassengerId(ticket.getPassengerId().toString());
////        var passengerName = ticket.getPassengerName();
////        var contactData = new MapConverter().convertToDatabaseColumn(ticket.getContactData());
////
////        try (var con = ConnectionManager.open()) {
////
////            if (isTicketExist(con, ticketNo)) {
////                throw new AlreadyExistException("Ticket " + ticketNo + ", already exist");
////            }
////            try (var stmt = con.prepareStatement(ticketSqlQuery.getNEW_TICKET())) {
////
////                stmt.setString(1, ticketNo);
////                stmt.setString(2, bookRef);
////                stmt.setString(3, passengerId);
////                stmt.setString(4, passengerName);
////                stmt.setObject(5, contactData, java.sql.Types.OTHER);
////
////                var row = stmt.executeUpdate();
////                if (row == 0) {
////                    throw new SaveException("Failed to save a ticket.");
////                }
////            }
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////        return find(userId, ticketNo);
//
//
//        return ticket;
//    }
//
//
//    @Override
//    public Optional<Ticket> find(Long userId, String ticketNo) {
//        Optional<Ticket> ticketLiteDtoResponse = null;
//        try (var con = ConnectionManager.open()) {
//
//            if (!isTicketExist(con, ticketNo)) {
//                throw new NotFoundException("Ticket " + ticketNo + ", does not exist");
//            }
////            ticketLiteDtoResponse = fetch(con, ticketNo);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return ticketLiteDtoResponse;
//    }
//
//    @Override
//    public List<Ticket> findAll(Long aLong, Integer integer) {
//        return null;
//    }
//
////    public List<Optional<TicketLiteDtoResponse>> findAll(Long userId, Integer limit) {
////        List<Optional<TicketLiteDtoResponse>> tickets = new ArrayList<>();
////
////        try (var con = ConnectionManager.open();
////             var stmt = con.prepareStatement(sqlQuery.getALL_TICKETS_WITH_LIMIT())) {
////           stmt.setInt(1, limit);
////            var rs = stmt.executeQuery();
////            while (rs.next()) {
////                String ticketNo = rs.getString("ticket_no");
////
////                Optional<TicketLiteDtoResponse> ticket;
////
////                ticket = fetch(con, ticketNo);
////                tickets.add(ticket);
////            }
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////        return tickets;
////    }
//
//    @Override
//    public Optional<Ticket> update(Long userId, TicketDto ticket) {
//        Optional<Ticket> update = null;
////        Long ticketNo = Long.valueOf(ticket.getTicketNo());
////        String bookRef = ticket.getBookRef();
////
////        String ticketNoAsString = String.format("%013d", ticket.getTicketNo());
////
////        try (var con = ConnectionManager.open()) {
////            if (!isTicketExist(con, ticketNoAsString)) {
////                throw new NotFoundException("Ticket " + ticketNo + ", does not exist");
////            }
////
////            try (var stmt = con.prepareStatement(ticketSqlQuery.getUPDATE_TICKET())) {
////                stmt.setString(1, bookRef);
////                stmt.setString(2, formatPassengerIdToString(ticket.getPassengerId()));
////                stmt.setString(3, ticket.getPassengerName());
////
////                String contactData = new MapConverter().convertToDatabaseColumn(ticket.getContactData());
////                stmt.setObject(4, contactData, java.sql.Types.OTHER);
////                stmt.setString(5, ticketNoAsString);
////
////                var row = stmt.executeUpdate();
////                if (row == 0) {
////                    throw new UpdateException("Failed to update the ticket: " + ticketNo);
////                }
////                return fetch(con, ticketNoAsString);
////            }
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
//        return update;
//    }
//
//    @Override
//    public boolean delete(Long userId, String ticketNo) {
//        try (var con = ConnectionManager.open()) {
//
//            if (!isTicketExist(con, ticketNo)) {
//                throw new NotFoundException("Ticket " + ticketNo + ", does not exist");
//            }
//            try (var stmt = con.prepareStatement(ticketSqlQuery.getDELETE_TICKET())) {
//                stmt.setString(1, ticketNo);
//                var rs = stmt.executeUpdate();
//                if (rs == 0) {
//                    throw new DeleteException("Failed to delete ticket: " + ticketNo);
//                }
//            }
//            if (!isTicketExist(con, ticketNo)) {
//                return true;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean deleteList(Long userId, List<String> ticketNos) {
//        for (String ticketNo : ticketNos) {
//            delete(userId, ticketNo);
//        }
//        return true;
//    }
//
//    public Optional<Ticket> fetch(Connection con, Long ticketNo) {
//        Ticket ticket = new Ticket();
//        List<String> bpCompositeKeys = new ArrayList<>();
//
//        try (var stmt = con.prepareStatement(ticketSqlQuery.getTICKET_BY_TICKET_NO());
//             var stmt2 = con.prepareStatement(boardingPassSqlQuery.getTICKET_NO_FLIGHT_ID_FROM_BOARDING_PASSES())) {
//            stmt2.setLong(1, ticketNo);
//
//            stmt.setLong(1, ticketNo);
//            var rs = stmt.executeQuery();
//            var rs2 = stmt2.executeQuery();
//            if (rs.next()) {
//                String bookRef = rs.getString("book_ref");
//                Long passengerId = Long.parseLong(rs.getString("passenger_id")
//                        .replaceAll("\\s", ""));
//                String passengerName = rs.getString("passenger_name");
//                String contactDataJson = rs.getString("contact_data") != null ?
//                        rs.getString("contact_data") : null;
//
//                Map<String, String> contactData = new MapConverter().convertToEntityAttribute(contactDataJson);
//                while (rs2.next()) {
//                    String formatted = rs2.getString(1) + "_" + rs2.getString(2);
//                    bpCompositeKeys.add(formatted);
//                }
//
//                ticket = new Ticket(
//                        ticketNo, bookRef, passengerId, passengerName, contactData);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.of(ticket);
//    }
//
//    private boolean isTicketExist(Connection con, String ticketNo) {
//        try (var stmt = con.prepareStatement(ticketSqlQuery.getCHECKING_TICKET_NO())) {
//
//            stmt.setString(1, ticketNo);
//            var rs = stmt.executeQuery();
//            return rs.next();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private String formatPassengerId(String passengerNo) {
//        if (passengerNo.length() > 4) {
//            return passengerNo.substring(0, 4) + " " + passengerNo.substring(4);
//        } else {
//            return passengerNo;
//        }
//    }
//
//    public String formatPassengerIdToString(long number) {
//        String numStr = String.valueOf(number);
//        StringBuilder sb = new StringBuilder();
//
//        IntStream.range(0, numStr.length())
//                .mapToObj(i -> i == 3 ? numStr.charAt(i) + " " : String.valueOf(numStr.charAt(i)))
//                .forEach(sb::append);
//
//        return sb.toString();
//    }
//
//}
//
