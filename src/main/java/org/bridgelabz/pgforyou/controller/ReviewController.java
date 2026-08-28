package org.bridgelabz.pgforyou.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bridgelabz.pgforyou.dto.request.ReviewRequestDTO;
import org.bridgelabz.pgforyou.dto.response.ReviewResponseDTO;
import org.bridgelabz.pgforyou.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //adding review to a pg
    @PostMapping("/pgs/{pgId}/reviews")
    public ResponseEntity<ReviewResponseDTO> addReview(@PathVariable Long pgId, @Valid @RequestBody ReviewRequestDTO requestDTO) {

        ReviewResponseDTO response = reviewService.addReview(pgId, requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //get all reviews of a pg
    @GetMapping("/pgs/{pgId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByPG(@PathVariable Long pgId) {

        return ResponseEntity.ok(reviewService.getReviewsByPG(pgId));
    }

    //get review by id
    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable Long id) {

        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    //update review
    @PutMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long id, @Valid @RequestBody ReviewRequestDTO requestDTO) {

        return ResponseEntity.ok(reviewService.updateReview(id, requestDTO));
    }

    //delete review
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {

        reviewService.deleteReview(id);

        return ResponseEntity.noContent().build();
    }
}