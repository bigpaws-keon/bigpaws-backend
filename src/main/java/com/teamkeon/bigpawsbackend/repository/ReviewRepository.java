package com.teamkeon.bigpawsbackend.repository;

import com.teamkeon.bigpawsbackend.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPlaceId(Long placeId);
}