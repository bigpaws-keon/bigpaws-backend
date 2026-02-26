package com.teamkeon.bigpawsbackend.controller;// com.teamkeon.bigpawsbackend.controller.ReviewController

import com.teamkeon.bigpawsbackend.domain.Review;
import com.teamkeon.bigpawsbackend.dto.ReviewCreateRequest;
import com.teamkeon.bigpawsbackend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places/{placeId}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public List<Review> getReviews(@PathVariable Long placeId) {
        return reviewService.getReviewsByPlace(placeId);
    }

    @PostMapping
    public Review createReview(@PathVariable Long placeId, @RequestBody ReviewCreateRequest request) {
        return reviewService.createReview(placeId, request);
    }
}
