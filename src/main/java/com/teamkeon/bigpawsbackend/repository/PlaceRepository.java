package com.teamkeon.bigpawsbackend.repository;

import com.teamkeon.bigpawsbackend.domain.Category;
import com.teamkeon.bigpawsbackend.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query("SELECT p FROM Place p WHERE " +
            "(:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:category IS NULL OR p.category = :category) AND " +
            "(:region IS NULL OR p.region = :region)")
    List<Place> searchPlaces(
            @Param("keyword") String keyword,
            @Param("category") Category category,
            @Param("region") String region
    );
}
