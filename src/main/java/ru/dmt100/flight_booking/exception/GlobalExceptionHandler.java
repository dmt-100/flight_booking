package ru.dmt100.flight_booking.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> handleAlreadyExistException(AlreadyExistException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Message", ex.getMessage());

        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Message", ex.getMessage());

        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<?> handleDeleteException(DeleteException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}