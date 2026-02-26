package com.teamkeon.bigpawsbackend.repository;

import com.teamkeon.bigpawsbackend.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}