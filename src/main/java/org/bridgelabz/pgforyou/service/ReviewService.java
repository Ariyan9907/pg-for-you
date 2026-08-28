package org.bridgelabz.pgforyou.service;

import lombok.RequiredArgsConstructor;
import org.bridgelabz.pgforyou.dto.request.ReviewRequestDTO;
import org.bridgelabz.pgforyou.dto.response.ReviewResponseDTO;
import org.bridgelabz.pgforyou.junittesting.PGNotFoundException;
import org.bridgelabz.pgforyou.junittesting.ReviewNotFoundException;
import org.bridgelabz.pgforyou.model.PG;
import org.bridgelabz.pgforyou.model.Review;
import org.bridgelabz.pgforyou.repository.PGRepository;
import org.bridgelabz.pgforyou.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PGRepository pgRepository;

    //adding review
    public ReviewResponseDTO addReview(Long pgId, ReviewRequestDTO requestDTO) {

        //find pg
        PG pg = pgRepository.findById(pgId).orElseThrow(() -> new PGNotFoundException("PG not found"));

        //create review
        Review review = createReview(new Review(), requestDTO);

        //connect review with pg
        review.setPg(pg);

        //save review
        Review savedReview = reviewRepository.save(review);

        return convertToResponseDTO(savedReview);
    }

    //get all reviews of a pg
    public List<ReviewResponseDTO> getReviewsByPG(Long pgId) {

        //check pg exists
        PG pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new PGNotFoundException("PG not found"));

        return reviewRepository.findByPg(pg)
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    //get review by id
    public ReviewResponseDTO getReviewById(Long id) {

        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        return convertToResponseDTO(review);
    }

    //update review
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO requestDTO) {

        Review oldReview = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        Review updatedReview = createReview(oldReview, requestDTO);

        Review savedReview = reviewRepository.save(updatedReview);

        return convertToResponseDTO(savedReview);
    }

    //delete review
    public void deleteReview(Long id) {

        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        reviewRepository.delete(review);
    }

    //create or update review
    private Review createReview(Review review, ReviewRequestDTO requestDTO) {

        review.setName(requestDTO.getName());
        review.setRating(requestDTO.getRating());
        review.setComment(requestDTO.getComment());

        return review;
    }

    //convert review entity to response dto
    private ReviewResponseDTO convertToResponseDTO(Review review) {

        ReviewResponseDTO responseDTO = new ReviewResponseDTO();

        responseDTO.setId(review.getId());
        responseDTO.setName(review.getName());
        responseDTO.setRating(review.getRating());
        responseDTO.setComment(review.getComment());
        responseDTO.setPgId(review.getPg().getId());

        return responseDTO;
    }
}