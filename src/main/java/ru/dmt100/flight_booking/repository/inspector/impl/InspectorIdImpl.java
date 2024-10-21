package ru.dmt100.flight_booking.repository.inspector.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.dmt100.flight_booking.entity.booking.repository.sql.BookingQuery;
import ru.dmt100.flight_booking.enums.TableType;
import ru.dmt100.flight_booking.exception.NotFoundException;
import ru.dmt100.flight_booking.repository.inspector.InspectorId;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class InspectorIdImpl<T> implements InspectorId<T> {

    private final BookingQuery bookingQuery;

    public InspectorIdImpl(@Qualifier("bookingQuery") BookingQuery bookingQuery) {
        this.bookingQuery = bookingQuery;
    }

    @Override
    public boolean inspect(Connection сonnection, TableType tableType, T key) {
        return switch (tableType) {
            case BOOKINGS -> {
                if (key instanceof String stringKey) {
                    yield inspectStringKey(сonnection, stringKey);
                } else {
                    throw new IllegalArgumentException("Expected String key type for BOOKINGS");
                }
            }
            default -> throw new IllegalArgumentException("Unsupported key type: " + key.getClass());

            case TICKETS -> {
                if (key instanceof Long longKey) {
                    yield inspectLongKey(сonnection, longKey);
                } else {
                    throw new IllegalArgumentException("Expected Long key type for TICKETS");
                }
            }
        };
    }

    private boolean inspectStringKey(Connection сonnection, String stringKey) {
        String query = bookingQuery.getCHECKING_BOOK_REF();
        try (var stmt = сonnection.prepareStatement(query)) {
            stmt.setString(1, stringKey);
            var rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new NotFoundException("Booking " + stringKey + " does not exist");
            } else {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean inspectLongKey(Connection con, Long longKey) {
//        String query = queryType.getQueryKey();
//        try (var stmt = con.prepareStatement(query)) {
//            stmt.setLong(1, longKey);
//            var rs = stmt.executeQuery();
//            if (!rs.next()) {
//                throw new NotFoundException("Ticket " + longKey + " does not exist");
//            } else {
//                return true;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


        return false;
    }

}
