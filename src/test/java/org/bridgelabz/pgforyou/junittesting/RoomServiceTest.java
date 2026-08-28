package org.bridgelabz.pgforyou.junittesting;

import org.bridgelabz.pgforyou.dto.response.RoomResponseDTO;
import org.bridgelabz.pgforyou.model.PG;
import org.bridgelabz.pgforyou.model.Room;
import org.bridgelabz.pgforyou.repository.RoomRepository;
import org.bridgelabz.pgforyou.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @Test
    void shouldGetRoomsByPG() {

        PG pg = new PG();

        pg.setId(1L);
        pg.setName("Sri Sai PG");


        Room room = new Room();

        room.setId(1L);
        room.setType("SINGLE");
        room.setRent(new BigDecimal("10000"));
        room.setTotalRooms(3);
        room.setAvailableRooms(3);
        room.setImageUrl("/images/rooms/single.jpg");
        room.setPg(pg);


        when(roomRepository.findByPg(pg));


        List<RoomResponseDTO> response =
                roomService.getRoomsByPG(1L);


        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("SINGLE", response.get(0).getType());
        assertEquals(3, response.get(0).getTotalRooms());
        assertEquals(3, response.get(0).getAvailableRooms());
    }
}