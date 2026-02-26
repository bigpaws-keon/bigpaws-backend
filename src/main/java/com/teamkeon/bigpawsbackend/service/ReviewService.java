package com.teamkeon.bigpawsbackend.service;

import com.teamkeon.bigpawsbackend.domain.Place;
import com.teamkeon.bigpawsbackend.domain.Review;
import com.teamkeon.bigpawsbackend.dto.ReviewCreateRequest;
import com.teamkeon.bigpawsbackend.repository.PlaceRepository;
import com.teamkeon.bigpawsbackend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;

    public List<Review> getReviewsByPlace(Long placeId) {
        return reviewRepository.findByPlaceId(placeId);
    }

    public Review createReview(Long placeId, ReviewCreateRequest request) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("장소를 찾을 수 없습니다."));

        Review review = Review.builder()
                .place(place)
                .content(request.getContent())
                .kindnessScore(request.getKindnessScore())
                .sizeScore(request.getSizeScore())
                .bigDogScore(request.getBigDogScore())
                .build();

        return reviewRepository.save(review);
    }
}
