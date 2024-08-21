package ru.dmt100.flight_booking.entity.flight.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.entity.flight.dto.record.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("flightServiceImpl")
@Transactional(readOnly = true)
public class FlightServiceImpl implements FlightService {

    @Override
    public List<String> findAllTicketsByFlightId(Long userId, Long flightId) {
        List<String> tickets = new ArrayList<>();
//        try (var con = ConnectionManager.open();
//             var stmt = con.prepareStatement(sqlQuery.getALL_TICKETS_ID_BY_FLIGHT_ID())) {
//            stmt.setLong(1, flightId);
//            try(var rs = stmt.executeQuery()) {
//                while(rs.next()) {
//                    tickets.add(rs.getString("ticket_no"));
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return tickets;
    }

    @Override
    public List<FlightCountByStatus> getFlightCountByStatus() {
        List<FlightCountByStatus> stats = new ArrayList<>();
//        FlightCountByStatus statusCount;
//        try (var con = ConnectionManager.open();
//             var stmt = con.prepareStatement(sqlQuery.getSTAT_FLIGHT_COUNT_BY_STATUS())) {
//            var rs = stmt.executeQuery();
//            while (rs.next()) {
//                Status status = Status.fromDbValue(rs.getString("status"));
//                int count = rs.getInt("flight_count");
//
//                statusCount = new FlightCountByStatus(status, count);
//                stats.add(statusCount);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return stats;
    }

    @Override
    public List<FlightsCountByMonth> getFlightsCountByMonth() {
        List<FlightsCountByMonth> stats = new ArrayList<>();
//        FlightsCountByMonth li;
//        try (var con = ConnectionManager.open();
//             var stmt = con.prepareStatement(sqlQuery.getSTAT_FLIGHT_COUNT_BY_MONTH())) {
//            var rs = stmt.executeQuery();
//            while (rs.next()) {
//                LocalDate m = rs.getTimestamp("month").toLocalDateTime().toLocalDate();
//                Integer fC = rs.getInt("flight_count");
//                stats.add(new FlightsCountByMonth(m, fC));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return stats;
    }

    @Override
    public List<DelayedFlightsWithPassengers> getDelayedFlightsWithPassengers() {
        List<DelayedFlightsWithPassengers> stats = new ArrayList<>();
//        try (var con = ConnectionManager.open();
//             var stmt = con.prepareStatement(sqlQuery.getSTAT_DELAYED_FLIGHTS_WITH_PASSENGERS())) {
//            var rs = stmt.executeQuery();
//            while (rs.next()) {
//                String fN = rs.getString("flight_no");
//                Integer pC = rs.getInt("passenger_count");
//                stats.add(new DelayedFlightsWithPassengers(fN, pC));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return stats;
    }

    @Override
    public List<FlightsDelayedMoreThanTwoHours> getFlightsDelayedMoreThanTwoHours() {
        List<FlightsDelayedMoreThanTwoHours> stats = new ArrayList<>();
//        try (var con = ConnectionManager.open();
//             var stmt = con.prepareStatement(sqlQuery.getSTAT_FLIGHTS_DELAYED_MORE_THAN_TWO_HOURS())) {
//            var rs = stmt.executeQuery();
//            while (rs.next()) {
//                Long fI = rs.getLong("flight_id");
//                LocalDate sD = rs.getTimestamp("scheduled_departure").toLocalDateTime().toLocalDate();
//                LocalDate aD = rs.getTimestamp("actual_departure").toLocalDateTime().toLocalDate();
//                Float dH = rs.getFloat("delay_hours");
//
//                stats.add(new FlightsDelayedMoreThanTwoHours(fI, sD, aD, dH));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return stats;
    }

    @Override
    public List<MostPopularRoutes> getMostPopularRoutes() {
        List<MostPopularRoutes> stats = new ArrayList<>();
//        try (var con = ConnectionManager.open();
//             var stmt = con.prepareStatement(sqlQuery.getSTAT_MOST_POPULAR_ROUTES())) {
//            var rs = stmt.executeQuery();
//            while (rs.next()) {
//                String dA = rs.getString("departure_airport");
//                String aA = rs.getString("arrival_airport");
//                Integer rC = rs.getInt("route_count");
//                stats.add(new MostPopularRoutes(dA, aA, rC));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return stats;
    }

    @Override
    public List<AvgDelayByDayOfWeek> getAvgDelayByDayOfWeek() {
        List<AvgDelayByDayOfWeek> stats = new ArrayList<>();
//        try (var con = ConnectionManager.open();
//             var stmt = con.prepareStatement(sqlQuery.getSTAT_AVG_DELAY_BY_DAY_OF_WEEK())) {
//            var rs = stmt.executeQuery();
//            while (rs.next()) {
//                String dOW = rs.getString("day_of_week");
//                Float aDH = rs.getFloat("average_delay_hours");
//                stats.add(new AvgDelayByDayOfWeek(dOW, aDH));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return stats;
    }

}
