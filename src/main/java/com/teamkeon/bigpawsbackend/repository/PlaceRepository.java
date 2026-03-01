package com.teamkeon.bigpawsbackend.repository;

import com.teamkeon.bigpawsbackend.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query(value = "SELECT * FROM place p WHERE " +
            "(CAST(:keyword AS TEXT) IS NULL OR LOWER(p.name) LIKE LOWER('%' || CAST(:keyword AS TEXT) || '%')) AND " +
            "(CAST(:category AS TEXT) IS NULL OR p.category = CAST(:category AS TEXT)) AND " +
            "(CAST(:region AS TEXT) IS NULL OR p.region = CAST(:region AS TEXT))",
            nativeQuery = true)
    List<Place> searchPlaces(
            @Param("keyword") String keyword,
            @Param("category") String category,
            @Param("region") String region
    );
}
