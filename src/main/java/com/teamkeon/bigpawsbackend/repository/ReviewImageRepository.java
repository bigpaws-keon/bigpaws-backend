package com.teamkeon.bigpawsbackend.repository;

import com.teamkeon.bigpawsbackend.domain.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    List<ReviewImage> findByReviewId(Long reviewId);
    List<ReviewImage> findByReviewPlaceId(Long placeId);
}
