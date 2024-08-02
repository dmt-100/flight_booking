package ru.dmt100.flight_booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.HttpHeaders;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Object> handleAlreadyExistException(AlreadyExistException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Message", ex.getMessage());

        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleAlreadyExistException(NotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Message", ex.getMessage());

        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.CONFLICT);
    }
}