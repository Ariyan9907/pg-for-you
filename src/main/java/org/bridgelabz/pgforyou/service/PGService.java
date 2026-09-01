package org.bridgelabz.pgforyou.service;

import lombok.RequiredArgsConstructor;
import org.bridgelabz.pgforyou.dto.request.PGRequestDTO;
import org.bridgelabz.pgforyou.dto.response.PGResponseDTO;
import org.bridgelabz.pgforyou.exception.PGNotFoundException;
import org.bridgelabz.pgforyou.model.PG;
import org.bridgelabz.pgforyou.repository.PGRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PGService {

    private final PGRepository pgRepository;

    //adding pg
    public PGResponseDTO addPG(PGRequestDTO requestDTO) {

        PG pg = createPG(new PG(), requestDTO);
        PG savedPG = pgRepository.save(pg);

        return convertToResponseDTO(savedPG);
    }

    //Get all pgs
    public List<PGResponseDTO> getAllPGs() {

        return pgRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    //get pg by id
    public PGResponseDTO getPGById(Long id) {

        PG pg = pgRepository.findById(id).orElseThrow(() -> new PGNotFoundException("PG not found"));
        return convertToResponseDTO(pg);
    }

    //update pg
    public PGResponseDTO updatePG(Long id, PGRequestDTO requestDTO) {

        PG oldPg = pgRepository.findById(id).orElseThrow(() -> new PGNotFoundException("PG not found"));
        PG updatedPg= createPG(oldPg, requestDTO);
        PG updatedPG = pgRepository.save(updatedPg);

        return convertToResponseDTO(updatedPG);
    }

    //find pgs by location
    public List<PGResponseDTO> searchByLocation(String location){
        return pgRepository.findByLocationContainingIgnoreCase(location).stream().map(this::convertToResponseDTO).toList();
    }

    //delete pg
    public void deletePG(Long id) {

        PG pg = pgRepository.findById(id).orElseThrow(() -> new PGNotFoundException("PG not found"));

        pgRepository.delete(pg);
    }

    //pagination and sorting
    public Page<PGResponseDTO> getPGsWithPagination(int page,int size){
        Pageable pageble = PageRequest.of(page, size);

        return pgRepository.findAll(pageble).map(this::convertToResponseDTO);
    }

    //get pgs with pagination and sorting
    public Page<PGResponseDTO> getPGsWithPaginationAndSorting(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        return pgRepository.findAll(pageable).map(this::convertToResponseDTO);
    }


    private PG createPG(PG pg, PGRequestDTO requestDTO) {
        pg.setName(requestDTO.getName());
        pg.setLocation(requestDTO.getLocation());
        pg.setRent(requestDTO.getRent());
        pg.setImageUrl(requestDTO.getImageUrl());

        return pg;
    }

    private PGResponseDTO convertToResponseDTO(PG pg) {

        PGResponseDTO responseDTO = new PGResponseDTO();

        responseDTO.setId(pg.getId());
        responseDTO.setName(pg.getName());
        responseDTO.setLocation(pg.getLocation());
        responseDTO.setRent(pg.getRent());
        responseDTO.setImageUrl(pg.getImageUrl());

        return responseDTO;
    }
}