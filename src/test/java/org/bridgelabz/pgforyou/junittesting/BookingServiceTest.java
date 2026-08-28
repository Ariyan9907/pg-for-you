package org.bridgelabz.pgforyou.junittesting;

import org.bridgelabz.pgforyou.dto.request.BookingRequestDTO;
import org.bridgelabz.pgforyou.dto.response.BookingResponseDTO;
import org.bridgelabz.pgforyou.model.Booking;
import org.bridgelabz.pgforyou.model.Room;
import org.bridgelabz.pgforyou.repository.BookingRepository;
import org.bridgelabz.pgforyou.repository.RoomRepository;
import org.bridgelabz.pgforyou.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void shouldAddBookingSuccessfully() {

        BookingRequestDTO requestDTO =
                new BookingRequestDTO();

        requestDTO.setName("Aryan");
        requestDTO.setPhone("9876543210");
        requestDTO.setRoomId(1L);


        Room room = new Room();

        room.setId(1L);
        room.setTotalRooms(3);
        room.setAvailableRooms(3);


        when(roomRepository.findById(1L))
                .thenReturn(Optional.of(room));


        Booking savedBooking = new Booking();

        savedBooking.setId(1L);
        savedBooking.setName("Aryan");
        savedBooking.setPhone("9876543210");
        savedBooking.setStatus("CONFIRMED");
        savedBooking.setRoom(room);


        when(bookingRepository.save(any(Booking.class)))
                .thenReturn(savedBooking);


        BookingResponseDTO response =
                bookingService.addBooking(requestDTO);


        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Aryan", response.getName());
        assertEquals("CONFIRMED", response.getStatus());
        assertEquals(1L, response.getRoomId());


        //available rooms should decrease
        assertEquals(2, room.getAvailableRooms());


        verify(roomRepository).save(room);
        verify(bookingRepository).save(any(Booking.class));
    }
}