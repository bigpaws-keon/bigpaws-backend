package com.teamkeon.bigpawsbackend.service;

import com.teamkeon.bigpawsbackend.domain.Place;
import com.teamkeon.bigpawsbackend.domain.Review;
import com.teamkeon.bigpawsbackend.domain.ReviewImage;
import com.teamkeon.bigpawsbackend.domain.User;
import com.teamkeon.bigpawsbackend.dto.ReviewCreateRequest;
import com.teamkeon.bigpawsbackend.dto.ReviewResponse;
import com.teamkeon.bigpawsbackend.repository.PlaceRepository;
import com.teamkeon.bigpawsbackend.repository.ReviewImageRepository;
import com.teamkeon.bigpawsbackend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final ReviewImageRepository reviewImageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public List<ReviewResponse> getReviewsByPlace(Long placeId) {
        return reviewRepository.findByPlaceId(placeId).stream()
                .map(this::toResponse)
                .toList();
    }

    public ReviewResponse createReview(Long placeId, ReviewCreateRequest request, List<MultipartFile> images) {
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

        Review saved = reviewRepository.save(review);

        if (images != null && !images.isEmpty()) {
            saveImages(saved, images);
        }

        return toResponse(saved);
    }

    public ReviewResponse updateReview(Long reviewId, ReviewCreateRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!review.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("본인의 리뷰만 수정할 수 있습니다.");
        }
        review.update(request.getContent(), request.getKindnessScore(), request.getSizeScore(), request.getBigDogScore());
        return toResponse(reviewRepository.save(review));
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!review.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("본인의 리뷰만 삭제할 수 있습니다.");
        }
        reviewImageRepository.deleteAll(reviewImageRepository.findByReviewId(reviewId));
        reviewRepository.delete(review);
    }

    private void saveImages(Review review, List<MultipartFile> images) {
        try {
            Path dir = Paths.get(uploadPath, "reviews", review.getId().toString()).toAbsolutePath();
            Files.createDirectories(dir);

            for (MultipartFile file : images) {
                if (file.isEmpty()) continue;

                String originalName = file.getOriginalFilename();
                String ext = originalName != null && originalName.contains(".")
                        ? originalName.substring(originalName.lastIndexOf("."))
                        : ".jpg";
                String storedName = UUID.randomUUID() + ext;
                Path filePath = dir.resolve(storedName);
                file.transferTo(filePath.toAbsolutePath().toFile());

                ReviewImage image = ReviewImage.builder()
                        .fileName(originalName != null ? originalName : storedName)
                        .storagePath("reviews/" + review.getId() + "/" + storedName)
                        .review(review)
                        .build();
                reviewImageRepository.save(image);
            }
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장에 실패했습니다.", e);
        }
    }

    private ReviewResponse toResponse(Review review) {
        List<String> imageUrls = reviewImageRepository.findByReviewId(review.getId()).stream()
                .map(img -> "/uploads/" + img.getStoragePath())
                .toList();

        return ReviewResponse.builder()
                .id(review.getId())
                .content(review.getContent())
                .kindnessScore(review.getKindnessScore())
                .sizeScore(review.getSizeScore())
                .bigDogScore(review.getBigDogScore())
                .nickname(review.getUser() != null ? review.getUser().getNickname() : "익명")
                .imageUrls(imageUrls)
                .build();
    }
}
