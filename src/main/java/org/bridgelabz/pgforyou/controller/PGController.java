package org.bridgelabz.pgforyou.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bridgelabz.pgforyou.dto.request.PGRequestDTO;
import org.bridgelabz.pgforyou.dto.response.PGResponseDTO;
import org.bridgelabz.pgforyou.service.PGService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pgs")
@RequiredArgsConstructor
public class PGController {

    private final PGService pgService;

    @PostMapping
    public ResponseEntity<PGResponseDTO> addPG(@Valid @RequestBody PGRequestDTO requestDTO) {

        PGResponseDTO response = pgService.addPG(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PGResponseDTO>> getAllPGs() {

        return ResponseEntity.ok(pgService.getAllPGs());
    }

    //search pgs by location
    @GetMapping("/search")
    public ResponseEntity<List<PGResponseDTO>> searchByLocation(@RequestParam String location) {

        return ResponseEntity.ok(pgService.searchByLocation(location));
    }

    //get pgs with pagination
    @GetMapping("/page")
    public ResponseEntity<Page<PGResponseDTO>> getPGsWithPagination(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(pgService.getPGsWithPagination(page, size));
    }

    //get pgs with pagination and sorting
    @GetMapping("/page/sort")
    public ResponseEntity<Page<PGResponseDTO>> getPGsWithPaginationAndSorting(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "name") String sortBy) {

        return ResponseEntity.ok(pgService.getPGsWithPaginationAndSorting(page, size, sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PGResponseDTO> getPGById(@PathVariable Long id) {

        return ResponseEntity.ok(pgService.getPGById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PGResponseDTO> updatePG(@PathVariable Long id, @Valid @RequestBody PGRequestDTO requestDTO) {

        return ResponseEntity.ok(pgService.updatePG(id, requestDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePG(@PathVariable Long id) {

        pgService.deletePG(id);

        return ResponseEntity.noContent().build();
    }
}