package com.teamkeon.bigpawsbackend.controller;

import com.teamkeon.bigpawsbackend.dto.ReviewCreateRequest;
import com.teamkeon.bigpawsbackend.dto.ReviewResponse;
import com.teamkeon.bigpawsbackend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ReviewResponse createReview(
            @PathVariable Long placeId,
            @RequestPart("review") ReviewCreateRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        return reviewService.createReview(placeId, request, images);
    }

    @PutMapping("/{reviewId}")
    public ReviewResponse updateReview(
            @PathVariable Long placeId,
            @PathVariable Long reviewId,
            @RequestBody ReviewCreateRequest request) {
        return reviewService.updateReview(reviewId, request);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long placeId, @PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
