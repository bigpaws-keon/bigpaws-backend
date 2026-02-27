package com.teamkeon.bigpawsbackend.service;

import com.teamkeon.bigpawsbackend.domain.Place;
import com.teamkeon.bigpawsbackend.domain.Review;
import com.teamkeon.bigpawsbackend.domain.User;
import com.teamkeon.bigpawsbackend.dto.ReviewCreateRequest;
import com.teamkeon.bigpawsbackend.dto.ReviewResponse;
import com.teamkeon.bigpawsbackend.repository.PlaceRepository;
import com.teamkeon.bigpawsbackend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;

    public List<ReviewResponse> getReviewsByPlace(Long placeId) {
        return reviewRepository.findByPlaceId(placeId).stream()
                .map(this::toResponse)
                .toList();
    }

    public ReviewResponse createReview(Long placeId, ReviewCreateRequest request) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("장소를 찾을 수 없습니다."));

        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Review review = Review.builder()
                .place(place)
                .user(user)
                .content(request.getContent())
                .kindnessScore(request.getKindnessScore())
                .sizeScore(request.getSizeScore())
                .bigDogScore(request.getBigDogScore())
                .build();

        return toResponse(reviewRepository.save(review));
    }

    private ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .content(review.getContent())
                .kindnessScore(review.getKindnessScore())
                .sizeScore(review.getSizeScore())
                .bigDogScore(review.getBigDogScore())
                .nickname(review.getUser() != null ? review.getUser().getNickname() : "익명")
                .build();
    }
}
