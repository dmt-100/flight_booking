package ru.dmt100.flight_booking.util;

import lombok.AllArgsConstructor;
import ru.dmt100.flight_booking.boardingPass.model.BoardingPass;
import ru.dmt100.flight_booking.sql.SqlQuery;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class Validator {

    private final SqlQuery sqlQuery;

    public boolean checkBoardingPass(Connection con, BoardingPass bp) {

        try (var stmt = con.prepareStatement(sqlQuery.getCHECKING_BOARDING_PASS())) {
            stmt.setString(1, bp.getTicketNo());
            stmt.setLong(2, bp.getFlightId());
            try (var rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkBoardingPass(Connection con, String ticketNo) {
        try (var stmt = con.prepareStatement(sqlQuery.getCHECKING_BOARDING_PASS_NO())) {
            stmt.setString(1, ticketNo);
            try (var rs = stmt.executeQuery()) {
                if(rs.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }


}
