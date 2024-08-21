package ru.dmt100.flight_booking.entity.boardingPass.sql.query;

import org.springframework.stereotype.Repository;

@Repository("boardingPassSqlQuery")
public class BoardingPassSqlQuery {

    public String getTICKET_NO_FLIGHT_ID_FROM_BOARDING_PASSES() {
        return TICKET_NO_FLIGHT_ID_FROM_BOARDING_PASSES;
    }
    public String getCHECKING_BOARDING_PASS() {
        return CHECKING_BOARDING_PASS;
    }
    public String getCHECKING_BOARDING_PASS_NO() {
        return CHECKING_BOARDING_PASS_NO;
    }
    public String getNEW_BOARDING_PASS() {
        return NEW_BOARDING_PASS;
    }
    public String getALL_BOARDING_PASSES() {
        return ALL_BOARDING_PASSES;
    }
    public String getBOARDING_PASS_BY_BOARDING_NO() {
        return BOARDING_PASS_BY_BOARDING_NO;
    }
    public String getUPDATE_BOARDING_PASS() {
        return UPDATE_BOARDING_PASS;
    }
    public String getUPDATE_BOARDING_PASS_SEAT() {
        return UPDATE_BOARDING_PASS_SEAT;
    }
    public String getDELETE_BOARDING_PASS() {
        return DELETE_BOARDING_PASS;
    }
    public String getDELETE_BOARDING_PASSES_BY_BOOK_REF() {
        return DELETE_BOARDING_PASSES_BY_BOOK_REF;
    }


    // Validation
    // Checking the existence of a boarding pass with a composite key
    private final String CHECKING_BOARDING_PASS = """
            select 1 
            from boarding_passes 
            where ticket_no = ? and flight_id = ?;
            """;
    // Checks if a boarding pass with a specific ticket number exists in the boarding_passes table.
    private final String CHECKING_BOARDING_PASS_NO = """
            select boarding_no 
            from boarding_passes 
            where ticket_no = ?
            """;

    // Creates a new boarding pass
    private final String TICKET_NO_FLIGHT_ID_FROM_BOARDING_PASSES = """
            select ticket_no, flight_id from boarding_passes bp
            where ticket_no = ?
            """;
    private final String NEW_BOARDING_PASS = """
            insert into boarding_passes (ticket_no, flight_id, boarding_no, seat_no)
            values (?, ?, ?, ?)
            """;

    // Selects all boarding passes
    private final String ALL_BOARDING_PASSES = """
            select ticket_no, flight_id, boarding_no, seat_no 
            from boarding_passes 
            limit ?
            """;

    // selects a boarding pass by ticket number
    private final String BOARDING_PASS_BY_BOARDING_NO = """
            select ticket_no, flight_id, boarding_no, seat_no from boarding_passes
            where ticket_no = ?
            """;

    // updates a boarding pass by ticket number
    private final String UPDATE_BOARDING_PASS = """
            update boarding_passes
            set flight_id = ?, boarding_no = ?, seat_no = ?
            where ticket_no = ?
            """;

    private final String UPDATE_BOARDING_PASS_SEAT = """
                    update boarding_passes
                    set seat_no = ?
                    where flight_id = ? and boarding_no = ?
            """;

    // deletes a boarding pass by ticket number
    private final String DELETE_BOARDING_PASS = """
            delete from boarding_passes where ticket_no = ? and flight_id = ?
            """;
    // deletes all boarding passes bybook_ref.
    private final String DELETE_BOARDING_PASSES_BY_BOOK_REF = """
            delete from boarding_passes
            where ticket_no in (
                select ticket_no
                from tickets
                where book_ref = ?
            );
            """;
}
