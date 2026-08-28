package org.bridgelabz.pgforyou.controller;

import lombok.RequiredArgsConstructor;
import org.bridgelabz.pgforyou.dto.request.BookingRequestDTO;
import org.bridgelabz.pgforyou.dto.request.ReviewRequestDTO;
import org.bridgelabz.pgforyou.dto.response.BookingResponseDTO;
import org.bridgelabz.pgforyou.dto.response.PGResponseDTO;
import org.bridgelabz.pgforyou.dto.response.ReviewResponseDTO;
import org.bridgelabz.pgforyou.dto.response.RoomResponseDTO;
import org.bridgelabz.pgforyou.service.BookingService;
import org.bridgelabz.pgforyou.service.PGService;
import org.bridgelabz.pgforyou.service.ReviewService;
import org.bridgelabz.pgforyou.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final PGService pgService;
    private final RoomService roomService;
    private final BookingService bookingService;
    private final ReviewService reviewService;


    //show all pgs
    @GetMapping("/")
    public String home(Model model) {

        List<PGResponseDTO> pgs =
                pgService.getAllPGs();

        model.addAttribute("pgs", pgs);

        return "home";
    }


    //search pgs by location
    @GetMapping("/search")
    public String searchPGs(
            @RequestParam String location,
            Model model) {

        List<PGResponseDTO> pgs =
                pgService.searchByLocation(location);

        model.addAttribute("pgs", pgs);
        model.addAttribute("searchLocation", location);

        return "home";
    }


    //show pg details, rooms and reviews
    @GetMapping("/pgs/{id}")
    public String pgDetails(
            @PathVariable Long id,
            Model model) {

        PGResponseDTO pg =
                pgService.getPGById(id);

        List<RoomResponseDTO> rooms =
                roomService.getRoomsByPG(id);

        List<ReviewResponseDTO> reviews =
                reviewService.getReviewsByPG(id);

        model.addAttribute("pg", pg);
        model.addAttribute("rooms", rooms);
        model.addAttribute("reviews", reviews);

        return "pg-details";
    }


    //show booking form
    @GetMapping("/bookings/new")
    public String bookingForm(
            @RequestParam Long roomId,
            Model model) {

        RoomResponseDTO room =
                roomService.getRoomById(roomId);

        model.addAttribute("room", room);

        return "booking";
    }


    //submit booking form
    @PostMapping("/bookings")
    public String submitBooking(
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam Long roomId,
            Model model) {

        BookingRequestDTO requestDTO =
                new BookingRequestDTO();

        requestDTO.setName(name);
        requestDTO.setPhone(phone);
        requestDTO.setRoomId(roomId);

        BookingResponseDTO booking =
                bookingService.addBooking(requestDTO);

        model.addAttribute("booking", booking);

        return "booking-success";
    }


    //show all bookings
    @GetMapping("/bookings")
    public String bookings(Model model) {

        List<BookingResponseDTO> bookings =
                bookingService.getAllBookings();

        model.addAttribute("bookings", bookings);

        return "bookings";
    }


    //show review form
    @GetMapping("/pgs/{pgId}/reviews/new")
    public String reviewForm(
            @PathVariable Long pgId,
            Model model) {

        PGResponseDTO pg =
                pgService.getPGById(pgId);

        model.addAttribute("pg", pg);

        return "review";
    }


    //submit review form
    @PostMapping("/pgs/{pgId}/reviews")
    public String submitReview(
            @PathVariable Long pgId,
            @RequestParam String name,
            @RequestParam Integer rating,
            @RequestParam String comment) {

        ReviewRequestDTO requestDTO =
                new ReviewRequestDTO();

        requestDTO.setName(name);
        requestDTO.setRating(rating);
        requestDTO.setComment(comment);

        reviewService.addReview(pgId, requestDTO);

        return "redirect:/pgs/" + pgId;
    }


    //cancel booking
    @PostMapping("/bookings/{id}/cancel")
    public String cancelBooking(
            @PathVariable Long id) {

        bookingService.cancelBooking(id);

        return "redirect:/bookings";
    }
}