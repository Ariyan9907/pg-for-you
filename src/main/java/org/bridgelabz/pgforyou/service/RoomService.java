package org.bridgelabz.pgforyou.service;

import lombok.RequiredArgsConstructor;
import org.bridgelabz.pgforyou.dto.request.RoomRequestDTO;
import org.bridgelabz.pgforyou.dto.response.RoomResponseDTO;
import org.bridgelabz.pgforyou.exception.PGNotFoundException;
import org.bridgelabz.pgforyou.exception.RoomNotFoundException;
import org.bridgelabz.pgforyou.model.PG;
import org.bridgelabz.pgforyou.model.Room;
import org.bridgelabz.pgforyou.repository.PGRepository;
import org.bridgelabz.pgforyou.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final PGRepository pgRepository;

    //adding room
    public RoomResponseDTO addRoom(Long pgId, RoomRequestDTO requestDTO) {

        PG pg = pgRepository.findById(pgId).orElseThrow(() -> new PGNotFoundException("PG not found"));

        Room room = createRoom(new Room(), requestDTO);

        room.setPg(pg);

        Room savedRoom = roomRepository.save(room);

        return convertToResponseDTO(savedRoom);
    }

    //get all rooms of a pg
    public List<RoomResponseDTO> getRoomsByPG(Long pgId) {

        PG pg = pgRepository.findById(pgId).orElseThrow(() -> new PGNotFoundException("PG not found"));

        return roomRepository.findByPg(pg)
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    //get room by id
    public RoomResponseDTO getRoomById(Long id) {

        Room room = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException("Room not found"));

        return convertToResponseDTO(room);
    }

    //update room
    public RoomResponseDTO updateRoom(Long id, RoomRequestDTO requestDTO) {

        Room oldRoom = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException("Room not found"));

        Room updatedRoom = createRoom(oldRoom, requestDTO);

        Room savedRoom = roomRepository.save(updatedRoom);

        return convertToResponseDTO(savedRoom);
    }

    //delete room
    public void deleteRoom(Long id) {

        Room room = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException("Room not found"));

        roomRepository.delete(room);
    }

    private Room createRoom(Room room, RoomRequestDTO requestDTO) {

        room.setType(requestDTO.getType());
        room.setRent(requestDTO.getRent());
        room.setTotalRooms(requestDTO.getTotalRooms());
        room.setAvailableRooms(requestDTO.getAvailableRooms());
        room.setImageUrl(requestDTO.getImageUrl());

        return room;
    }

    private RoomResponseDTO convertToResponseDTO(Room room) {

        RoomResponseDTO responseDTO = new RoomResponseDTO();

        responseDTO.setId(room.getId());
        responseDTO.setType(room.getType());
        responseDTO.setRent(room.getRent());
        responseDTO.setTotalRooms(room.getTotalRooms());
        responseDTO.setAvailableRooms(room.getAvailableRooms());
        responseDTO.setImageUrl(room.getImageUrl());
        responseDTO.setPgId(room.getPg().getId());

        return responseDTO;
    }
}