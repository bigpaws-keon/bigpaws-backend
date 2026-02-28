package com.teamkeon.bigpawsbackend.repository;

import com.teamkeon.bigpawsbackend.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserIdAndPlaceId(Long userId, Long placeId);
    boolean existsByUserIdAndPlaceId(Long userId, Long placeId);
    List<Bookmark> findByUserId(Long userId);
    void deleteByUserIdAndPlaceId(Long userId, Long placeId);
}
