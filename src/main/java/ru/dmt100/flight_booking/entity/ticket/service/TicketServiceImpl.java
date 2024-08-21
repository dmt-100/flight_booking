//package ru.dmt100.flight_booking.entity.ticket.service;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.dmt100.flight_booking.entity.ticket.model.dto.TicketDto;
//import ru.dmt100.flight_booking.entity.ticket.model.dto.record.PassengersInfoByFlightId;
//import ru.dmt100.flight_booking.entity.ticket.model.dto.record.TicketOnScheduledFlightsByTimeRange;
//import ru.dmt100.flight_booking.enums.Status;
//import ru.dmt100.flight_booking.exception.NotFoundException;
//import ru.dmt100.flight_booking.entity.ticket.dao.TicketDaoImpl;
//import ru.dmt100.flight_booking.entity.ticket.sql.query.TicketSqlQuery;
//import ru.dmt100.flight_booking.util.ConnectionManager;
//
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Service("ticketServiceImpl")
//@AllArgsConstructor
//@Transactional(readOnly = true)
//public class TicketServiceImpl implements TicketService {
//    private final TicketDaoImpl ticketDao;
//    private final TicketSqlQuery ticketSqlQuery;
//
//    @Override
//    public List<Optional<TicketDto>> findPassengersInfoByListOfTicketNos(
//            Long userId, List<String> passengersTicketNos) {
//        List<Optional<TicketDto>> passList = new ArrayList<>();
////        Optional<TicketDto> pass;
////        try (var con = ConnectionManager.open()) {
////
////            for (String ticketNo : passengersTicketNos) {
////                pass = ticketDao.fetch(con, ticketNo);
////                assert false;
////                passList.add(pass);
////
////            }
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
//        return passList;
//    }
//
//    @Override
//    public List<PassengersInfoByFlightId> findPassengersInfoByFlightId(Long userId, Long flightId) {
//        List<PassengersInfoByFlightId> passengerInfos = new ArrayList<>();
//
//        try (var con = ConnectionManager.open();
//             var stmt = con.prepareStatement(ticketSqlQuery.getPASSENGERS_INFO_BY_FLIGHT_ID())) {
//
//            stmt.setLong(1, flightId);
//
//            try (var rs = stmt.executeQuery()) {
//                if (!rs.next()) {
//                    throw new NotFoundException("Flight " + flightId + ", does not exist");
//                }
//                while (rs.next()) {
//                    Long ticketNo = rs.getLong("ticket_no");
//                    String bookRef = rs.getString("book_ref");
//                    Long passengerId = Long.valueOf(rs.getString("passenger_id").replace(" ", ""));
//                    String passengerName = rs.getString("passenger_name");
//                    String contactData = rs.getString("contact_data");
//
//                    PassengersInfoByFlightId info = new PassengersInfoByFlightId(
//                             ticketNo, bookRef, passengerId, passengerName, contactData);
//
//                    passengerInfos.add(info);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return passengerInfos;
//    }
//
//    @Override
//    public List<TicketOnScheduledFlightsByTimeRange> getCountTicketsOnScheduledFlightsByTimeRange(
//            Long userId, LocalDateTime start, LocalDateTime end) {
//        List<TicketOnScheduledFlightsByTimeRange> stats = new ArrayList<>();
//        try(var con = ConnectionManager.open();
//        var stmt = con.prepareStatement(
//                ticketSqlQuery.getCOUNT_TICKETS_ON_SCHEDULED_FLIGHTS_BY_TIME_RANGE())) {
//
//            stmt.setTimestamp(1, Timestamp.valueOf(start));
//            stmt.setTimestamp(2, Timestamp.valueOf(end));
//
//            var rs = stmt.executeQuery();
//            while(rs.next()) {
//                String flightNo = rs.getString("flight_No");
//                LocalDateTime sDeparture = rs.getTimestamp("scheduled_departure").toLocalDateTime();
//                LocalDateTime sArrival = rs.getTimestamp("scheduled_arrival").toLocalDateTime();
//                Status status = Status.fromDbValue(rs.getString("status"));
//                Integer ticketCount = rs.getInt("ticket_count");
//
//                var ticketOnScheduled = new TicketOnScheduledFlightsByTimeRange(
//                        flightNo, sDeparture, sArrival, status, ticketCount
//                );
//                stats.add(ticketOnScheduled);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return stats;
//    }
//
//
//}
