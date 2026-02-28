package com.teamkeon.bigpawsbackend.repository;

import com.teamkeon.bigpawsbackend.domain.VisitPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitPlanRepository extends JpaRepository<VisitPlan, Long> {
    List<VisitPlan> findByPlaceIdAndVisitDateAfterOrderByVisitDateAsc(Long placeId, LocalDateTime after);
    List<VisitPlan> findByUserIdOrderByVisitDateDesc(Long userId);
}
