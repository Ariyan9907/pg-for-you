package org.bridgelabz.pgforyou.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bridgelabz.pgforyou.dto.request.RoomRequestDTO;
import org.bridgelabz.pgforyou.dto.response.RoomResponseDTO;
import org.bridgelabz.pgforyou.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    //adding room to a pg
    @PostMapping("/pgs/{pgId}/rooms")
    public ResponseEntity<RoomResponseDTO> addRoom(@PathVariable Long pgId, @Valid @RequestBody RoomRequestDTO requestDTO) {

        RoomResponseDTO response = roomService.addRoom(pgId, requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //get all rooms of a pg
    @GetMapping("/pgs/{pgId}/rooms")
    public ResponseEntity<List<RoomResponseDTO>> getRoomsByPG(@PathVariable Long pgId) {

        return ResponseEntity.ok(roomService.getRoomsByPG(pgId));
    }

    //get room by id
    @GetMapping("/rooms/{id}")
    public ResponseEntity<RoomResponseDTO> getRoomById(@PathVariable Long id) {

        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    //update room
    @PutMapping("/rooms/{id}")
    public ResponseEntity<RoomResponseDTO> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomRequestDTO requestDTO) {

        return ResponseEntity.ok(roomService.updateRoom(id, requestDTO));
    }

    //delete room
    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {

        roomService.deleteRoom(id);

        return ResponseEntity.noContent().build();
    }
}