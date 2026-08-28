package org.bridgelabz.pgforyou.junittesting;

import org.bridgelabz.pgforyou.dto.request.ReviewRequestDTO;
import org.bridgelabz.pgforyou.dto.response.ReviewResponseDTO;
import org.bridgelabz.pgforyou.model.PG;
import org.bridgelabz.pgforyou.model.Review;
import org.bridgelabz.pgforyou.repository.PGRepository;
import org.bridgelabz.pgforyou.repository.ReviewRepository;
import org.bridgelabz.pgforyou.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PGRepository pgRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void shouldAddReviewSuccessfully() {

        ReviewRequestDTO requestDTO =
                new ReviewRequestDTO();

        requestDTO.setName("Aryan");
        requestDTO.setRating(5);
        requestDTO.setComment(
                "Very good PG and excellent location."
        );


        PG pg = new PG();

        pg.setId(1L);
        pg.setName("Sri Sai PG");


        when(pgRepository.findById(1L))
                .thenReturn(Optional.of(pg));


        Review savedReview = new Review();

        savedReview.setId(1L);
        savedReview.setName("Aryan");
        savedReview.setRating(5);
        savedReview.setComment(
                "Very good PG and excellent location."
        );
        savedReview.setPg(pg);


        when(reviewRepository.save(any(Review.class)))
                .thenReturn(savedReview);


        ReviewResponseDTO response =
                reviewService.addReview(1L, requestDTO);


        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Aryan", response.getName());
        assertEquals(5, response.getRating());
        assertEquals(
                "Very good PG and excellent location.",
                response.getComment()
        );
    }
}