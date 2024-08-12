package ru.dmt100.flight_booking.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseUtil {

    public static ResponseEntity<?> headersMaker(double timeStart, List<?> list) {
        double qTime = (System.currentTimeMillis() - timeStart) / 1000;
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Processing-Time", qTime + " sec.");
        headers.add("X-Total-Records", String.valueOf(list.size()));

        return ResponseEntity.ok().headers(headers).body(list);
    }
}
