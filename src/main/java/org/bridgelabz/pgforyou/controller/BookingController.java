package org.bridgelabz.pgforyou.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bridgelabz.pgforyou.dto.request.BookingRequestDTO;
import org.bridgelabz.pgforyou.dto.response.BookingResponseDTO;
import org.bridgelabz.pgforyou.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    //adding booking
    @PostMapping
    public ResponseEntity<BookingResponseDTO> addBooking(@Valid @RequestBody BookingRequestDTO requestDTO) {

        BookingResponseDTO response = bookingService.addBooking(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //get all bookings
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {

        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    //get booking by id
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long id) {

        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    //cancel booking
    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable Long id) {

        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }
}