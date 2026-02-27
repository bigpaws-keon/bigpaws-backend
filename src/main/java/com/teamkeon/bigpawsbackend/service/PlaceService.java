package com.teamkeon.bigpawsbackend.service;

import com.teamkeon.bigpawsbackend.domain.Place;
import com.teamkeon.bigpawsbackend.domain.Review;
import com.teamkeon.bigpawsbackend.dto.PlaceCreateRequest;
import com.teamkeon.bigpawsbackend.dto.PlaceResponse;
import com.teamkeon.bigpawsbackend.repository.PlaceRepository;
import com.teamkeon.bigpawsbackend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    public List<PlaceResponse> getAllPlaces() {
        return placeRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public PlaceResponse getPlace(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("장소를 찾을 수 없습니다."));
        return toResponse(place);
    }

    public PlaceResponse createPlace(PlaceCreateRequest request) {
        Place place = Place.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        return toResponse(placeRepository.save(place));
    }

    private PlaceResponse toResponse(Place place) {
        List<Review> reviews = reviewRepository.findByPlaceId(place.getId());

        double avgKindness = reviews.stream().mapToInt(Review::getKindnessScore).average().orElse(0);
        double avgSize = reviews.stream().mapToInt(Review::getSizeScore).average().orElse(0);
        double avgBigDog = reviews.stream().mapToInt(Review::getBigDogScore).average().orElse(0);

        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .description(place.getDescription())
                .avgKindnessScore(Math.round(avgKindness * 10) / 10.0)
                .avgSizeScore(Math.round(avgSize * 10) / 10.0)
                .avgBigDogScore(Math.round(avgBigDog * 10) / 10.0)
                .reviewCount(reviews.size())
                .build();
    }
}