package org.bridgelabz.pgforyou.exception;

import org.bridgelabz.pgforyou.dto.response.ErrorResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void shouldReturnNotFoundErrorForMissingPG() {
        PGNotFoundException exception = new PGNotFoundException("PG not found with id 99");

        ResponseEntity<ErrorResponseDTO> response = exceptionHandler.handlePGNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals("PG not found with id 99", response.getBody().getMessage());
    }
}
