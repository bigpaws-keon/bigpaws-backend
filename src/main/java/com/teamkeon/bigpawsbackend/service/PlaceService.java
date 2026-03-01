package com.teamkeon.bigpawsbackend.service;

import com.teamkeon.bigpawsbackend.domain.Category;
import com.teamkeon.bigpawsbackend.domain.Place;
import com.teamkeon.bigpawsbackend.domain.Review;
import com.teamkeon.bigpawsbackend.dto.PlaceCreateRequest;
import com.teamkeon.bigpawsbackend.dto.PlaceResponse;
import com.teamkeon.bigpawsbackend.repository.PlaceRepository;
import com.teamkeon.bigpawsbackend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    public List<PlaceResponse> searchPlaces(String keyword, String category, String region, String sort) {
        String kw = (keyword != null && !keyword.isEmpty()) ? keyword : null;
        String cat = (category != null && !category.isEmpty()) ? category : null;
        String rg = (region != null && !region.isEmpty()) ? region : null;

        boolean noFilter = (kw == null && cat == null && rg == null);
        List<Place> places = noFilter ? placeRepository.findAll() : placeRepository.searchPlaces(kw, cat, rg);

        List<PlaceResponse> results = places.stream()
                .map(this::toResponse)
                .toList();

        return sortPlaces(results, sort);
    }

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
                .category(Category.valueOf(request.getCategory()))
                .region(request.getRegion())
                .district(request.getDistrict())
                .neighborhood(request.getNeighborhood())
                .mapUrl(request.getMapUrl())
                .build();

        return toResponse(placeRepository.save(place));
    }

    private List<PlaceResponse> sortPlaces(List<PlaceResponse> places, String sort) {
        if (sort == null) sort = "latest";

        return switch (sort) {
            case "rating" -> places.stream()
                    .sorted(Comparator.comparingDouble(PlaceResponse::getOverallScore).reversed())
                    .toList();
            case "reviews" -> places.stream()
                    .sorted(Comparator.comparingInt(PlaceResponse::getReviewCount).reversed())
                    .toList();
            default -> places.stream()
                    .sorted(Comparator.comparingLong(PlaceResponse::getId).reversed())
                    .toList();
        };
    }

    private PlaceResponse toResponse(Place place) {
        List<Review> reviews = reviewRepository.findByPlaceId(place.getId());

        double avgKindness = reviews.stream().mapToInt(Review::getKindnessScore).average().orElse(0);
        double avgSize = reviews.stream().mapToInt(Review::getSizeScore).average().orElse(0);
        double avgBigDog = reviews.stream().mapToInt(Review::getBigDogScore).average().orElse(0);

        double kindnessRounded = Math.round(avgKindness * 10) / 10.0;
        double sizeRounded = Math.round(avgSize * 10) / 10.0;
        double bigDogRounded = Math.round(avgBigDog * 10) / 10.0;
        double overallScore = Math.round((kindnessRounded + sizeRounded + bigDogRounded) / 3.0 * 10) / 10.0;

        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .description(place.getDescription())
                .category(place.getCategory().name())
                .region(place.getRegion())
                .district(place.getDistrict())
                .neighborhood(place.getNeighborhood())
                .mapUrl(place.getMapUrl())
                .avgKindnessScore(kindnessRounded)
                .avgSizeScore(sizeRounded)
                .avgBigDogScore(bigDogRounded)
                .overallScore(overallScore)
                .reviewCount(reviews.size())
                .build();
    }
}
