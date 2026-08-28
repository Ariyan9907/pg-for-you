package org.bridgelabz.pgforyou.junittesting;

import org.bridgelabz.pgforyou.dto.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //handle pg not found
    @ExceptionHandler(PGNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePGNotFound(PGNotFoundException exception) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    //handle room not found
    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleRoomNotFound(RoomNotFoundException exception) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    //handle booking not found
    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleBookingNotFound(BookingNotFoundException exception) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    //handle review not found
    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleReviewNotFound(ReviewNotFoundException exception) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    //handle room unavailable
    @ExceptionHandler(RoomUnavailableException.class)
    public ResponseEntity<ErrorResponseDTO> handleRoomUnavailable(RoomUnavailableException exception) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    //handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList()
                .toString();

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                message,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}