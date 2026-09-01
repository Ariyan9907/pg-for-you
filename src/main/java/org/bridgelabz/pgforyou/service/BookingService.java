package org.bridgelabz.pgforyou.service;

import lombok.RequiredArgsConstructor;
import org.bridgelabz.pgforyou.dto.request.BookingRequestDTO;
import org.bridgelabz.pgforyou.dto.response.BookingResponseDTO;
import org.bridgelabz.pgforyou.exception.BookingNotFoundException;
import org.bridgelabz.pgforyou.exception.RoomNotFoundException;
import org.bridgelabz.pgforyou.exception.RoomUnavailableException;
import org.bridgelabz.pgforyou.model.Booking;
import org.bridgelabz.pgforyou.model.Room;
import org.bridgelabz.pgforyou.repository.BookingRepository;
import org.bridgelabz.pgforyou.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    //adding booking
    @Transactional
    public BookingResponseDTO addBooking(BookingRequestDTO requestDTO) {

        //find room
        Room room = roomRepository.findById(requestDTO.getRoomId()).orElseThrow(() -> new RoomNotFoundException("Room not found"));

        //check room availability
        if (room.getAvailableRooms() <= 0) {
            throw new RoomUnavailableException("Room is not available");
        }

        //create booking
        Booking booking = new Booking();

        booking.setName(requestDTO.getName());
        booking.setPhone(requestDTO.getPhone());
        booking.setBookingDate(LocalDate.now());
        booking.setStatus("CONFIRMED");
        booking.setRoom(room);

        //decrease available rooms
        room.setAvailableRooms(room.getAvailableRooms() - 1);

        //save room availability
        roomRepository.save(room);

        //save booking
        Booking savedBooking = bookingRepository.save(booking);

        return convertToResponseDTO(savedBooking);
    }

    //get all bookings
    public List<BookingResponseDTO> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    //get booking by id
    public BookingResponseDTO getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        return convertToResponseDTO(booking);
    }

    //cancel booking
    @Transactional
    public BookingResponseDTO cancelBooking(Long id) {

        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        //check if booking is already cancelled
        if ("CANCELLED".equals(booking.getStatus())) {
            throw new BookingNotFoundException("Booking is already cancelled");
        }

        //get the room associated with booking
        Room room = booking.getRoom();

        //increase available rooms
        if (room.getAvailableRooms() < room.getTotalRooms()) {
            room.setAvailableRooms(room.getAvailableRooms() + 1);
        }

        //change booking status
        booking.setStatus("CANCELLED");

        //save updated room
        roomRepository.save(room);

        //save updated booking
        Booking updatedBooking = bookingRepository.save(booking);

        return convertToResponseDTO(updatedBooking);
    }

    //convert booking entity to response dto
    private BookingResponseDTO convertToResponseDTO(Booking booking) {

        BookingResponseDTO responseDTO = new BookingResponseDTO();

        responseDTO.setId(booking.getId());
        responseDTO.setName(booking.getName());
        responseDTO.setPhone(booking.getPhone());
        responseDTO.setBookingDate(booking.getBookingDate());
        responseDTO.setStatus(booking.getStatus());
        responseDTO.setRoomId(booking.getRoom().getId());

        return responseDTO;
    }
}