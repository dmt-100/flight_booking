package ru.dmt100.flight_booking.flight.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.geometric.PGpoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmt100.flight_booking.aircraft.dto.AircraftDto;
import ru.dmt100.flight_booking.airport.dto.AirportDto;
import ru.dmt100.flight_booking.dao.Dao;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.exception.SaveException;
import ru.dmt100.flight_booking.flight.dto.FlightDto;
import ru.dmt100.flight_booking.flight.model.Flight;
import ru.dmt100.flight_booking.sql.SqlQuery;
import ru.dmt100.flight_booking.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("flightDaoImpl")
@AllArgsConstructor
@Transactional(readOnly = true)
public class FlightDaoImpl implements Dao<Long, String, Integer, Flight, FlightDto> {
    SqlQuery sqlQuery;

    @Override
    public Optional<FlightDto> save(Long userId, Flight fDR) {
        Optional<FlightDto> flight;

        try (var con = ConnectionManager.open();
             var stmt = con.prepareStatement(sqlQuery.getNEW_FLIGHT(),
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, fDR.getFlightNo());
            stmt.setTimestamp(2, Timestamp.from(fDR.getScheduledDeparture().toInstant()));
            stmt.setTimestamp(3, Timestamp.from(fDR.getScheduledArrival().toInstant()));
            stmt.setString(4, String.valueOf(fDR.getDepartureAirport().getAirportCode()));
            stmt.setString(5, String.valueOf(fDR.getArrivalAirport().getAirportCode()));
            stmt.setString(6, fDR.getStatus().toString());
            stmt.setString(7, fDR.getAircraftCode().getAircraftCode());
            stmt.setTimestamp(8, fDR.getActualDeparture() != null ? Timestamp.from(fDR.getActualDeparture().toInstant()) : null);
            stmt.setTimestamp(9, fDR.getActualArrival() != null ? Timestamp.from(fDR.getActualArrival().toInstant()) : null);

            var row = stmt.executeUpdate();
            if (row == 0) {
                throw new SaveException("Failed to insert a flight.");
            }

            try (var rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    flight = fetch(con, Long.valueOf(Long.toString(id)));
                } else {
                    throw new SaveException("Failed to retrieve the generated flight ID.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flight;
    }


    @Override
    public Optional<FlightDto> find(Long userId, String flightId) {
        Optional<FlightDto> flight;
        try (var con = ConnectionManager.open()) {
            Long flightIdAsLong = Long.valueOf(flightId);
            if (!isFlightExist(con, flightIdAsLong)) {
                throw new NotFoundException("Flight " + flightIdAsLong + ", does not exist");
            }
            flight = fetch(con, flightIdAsLong);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flight;
    }

    @Override
    public List<FlightDto> findAll(Long aLong, Integer integer) {
        return null;
    }

//    @Override
//    public List<Optional<FlightDto>> findAll(Long userId, Integer limit) {
//        List<Optional<FlightDto>> flights = new ArrayList<>();
//
//        try (var con = ConnectionManager.open();
//             var stmt = con.prepareStatement(sqlQuery.getALL_FLIGHTS());
//             var rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                Long flightId = rs.getLong("flight_id");
//
//                Optional<FlightDto> flightDto;
//
//                flightDto = fetch(con, flightId);
//                flights.add(flightDto);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return flights;
//    }

    @Override
    public Optional<FlightDto> update(Long aLong, Flight flight) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long aLong, String s) {
        return false;
    }

    @Override
    public boolean deleteList(Long aLong, List<String> k) {
        return false;
    }

    private Optional<FlightDto> fetch(Connection con, Long flightId) {
        try (var stmt = con.prepareStatement(sqlQuery.getFLIGHT_BY_FLIGHT_ID())) {

            stmt.setLong(1, flightId);

            try (var rs = stmt.executeQuery()) {

                if (!rs.next()) {
                    throw new NotFoundException("Flight " + flightId + ", does not exist");
                } else {

                    FlightDto flight;
                    String flightNo = rs.getString("flight_no");

                    ZonedDateTime zDTScheduledDeparture = rs
                            .getTimestamp("scheduled_departure").toInstant()
                            .atZone(ZoneId.systemDefault());

                    ZonedDateTime zDTScheduledArrival = rs
                            .getTimestamp("scheduled_arrival").toInstant()
                            .atZone(ZoneId.systemDefault());

                    String airportD = rs.getString("departure_airport");
                    String airportA = rs.getString("arrival_airport");
                    String status = rs.getString("status");
                    String aircraftCode = rs.getString("aircraft_code");

                    ZonedDateTime zDTActualDeparture = rs.getTimestamp("actual_departure") != null ?
                            rs.getTimestamp("actual_departure")
                                    .toInstant().atZone(ZoneId.systemDefault()) : null;

                    ZonedDateTime zDTActualArrival = rs.getTimestamp("actual_arrival") != null ?
                            rs.getTimestamp("actual_arrival")
                                    .toInstant().atZone(ZoneId.systemDefault()) : null;

                    flight = new FlightDto(
                            flightId,
                            flightNo,
                            zDTScheduledDeparture,
                            zDTScheduledArrival,
                            airportD,
                            airportA,
                            status,
                            aircraftCode,
                            zDTActualDeparture,
                            zDTActualArrival);

                    return Optional.of(flight);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isFlightExist(Connection con, Long flightId) {
        try (var stmt = con.prepareStatement(sqlQuery.getCHECKING_FLIGHT_ID())) {

            stmt.setLong(1, flightId);
            var rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private AirportDto getAirport(Connection con, String airportCode) {
        AirportDto airport = null;
        try (var stmt = con.prepareStatement(sqlQuery.getAIRPORT_BY_CODE())) {
            stmt.setString(1, airportCode);

            try (var rs = stmt.executeQuery()) {

                while (rs.next()) {

                    String aCode = rs.getString("airport_code");
                    String aName = rs.getString("airport_name");
                    String city = rs.getString("city");
                    PGpoint coordinates = (PGpoint) rs.getObject("coordinates");
                    String tz = rs.getString("timezone");

                    airport = new AirportDto(
                            aCode,
                            aName,
                            city,
                            coordinates,
                            tz
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return airport;
    }

    private AircraftDto getAirctraftDto(Connection con, String aircraftCode) {
        AircraftDto aircraftDto = null;
        try (var stmt = con.prepareStatement(sqlQuery.getAIRCRAFT_BY_CODE())) {
            stmt.setString(1, aircraftCode);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String aCode = rs.getString("aircraft_code");
                    String model = rs.getString("model");
                    Integer range = rs.getInt("range");

                    aircraftDto = new AircraftDto(
                            aCode,
                            model,
                            range
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aircraftDto;
    }

}
