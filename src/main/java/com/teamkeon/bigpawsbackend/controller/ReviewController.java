package com.teamkeon.bigpawsbackend.controller;

import com.teamkeon.bigpawsbackend.dto.ReviewCreateRequest;
import com.teamkeon.bigpawsbackend.dto.ReviewResponse;
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
    public List<ReviewResponse> getReviews(@PathVariable Long placeId) {
        return reviewService.getReviewsByPlace(placeId);
    }

    @PostMapping
    public ReviewResponse createReview(@PathVariable Long placeId, @RequestBody ReviewCreateRequest request) {
        return reviewService.createReview(placeId, request);
    }
}
