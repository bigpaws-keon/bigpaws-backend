package com.teamkeon.bigpawsbackend.service;

import com.teamkeon.bigpawsbackend.domain.Place;
import com.teamkeon.bigpawsbackend.domain.User;
import com.teamkeon.bigpawsbackend.domain.VisitPlan;
import com.teamkeon.bigpawsbackend.dto.VisitPlanRequest;
import com.teamkeon.bigpawsbackend.dto.VisitPlanResponse;
import com.teamkeon.bigpawsbackend.repository.PlaceRepository;
import com.teamkeon.bigpawsbackend.repository.VisitPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitPlanService {
    private final VisitPlanRepository visitPlanRepository;
    private final PlaceRepository placeRepository;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public VisitPlanResponse create(Long placeId, VisitPlanRequest request) {
        User user = getCurrentUser();
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("장소를 찾을 수 없습니다."));

        VisitPlan plan = VisitPlan.builder()
                .user(user)
                .place(place)
                .visitDate(LocalDateTime.parse(request.getVisitDate(), FMT))
                .dogName(request.getDogName())
                .dogBreed(request.getDogBreed())
                .dogSize(request.getDogSize())
                .memo(request.getMemo())
                .build();

        return toResponse(visitPlanRepository.save(plan));
    }

    public List<VisitPlanResponse> getByPlace(Long placeId) {
        return visitPlanRepository
                .findByPlaceIdAndVisitDateAfterOrderByVisitDateAsc(placeId, LocalDateTime.now())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<VisitPlanResponse> getMyPlans() {
        User user = getCurrentUser();
        return visitPlanRepository.findByUserIdOrderByVisitDateDesc(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void delete(Long planId) {
        VisitPlan plan = visitPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("방문 예고를 찾을 수 없습니다."));
        User user = getCurrentUser();
        if (!plan.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("본인의 방문 예고만 삭제할 수 있습니다.");
        }
        visitPlanRepository.delete(plan);
    }

    private VisitPlanResponse toResponse(VisitPlan plan) {
        return VisitPlanResponse.builder()
                .id(plan.getId())
                .nickname(plan.getUser().getNickname())
                .placeId(plan.getPlace().getId())
                .placeName(plan.getPlace().getName())
                .visitDate(plan.getVisitDate().format(FMT))
                .dogName(plan.getDogName())
                .dogBreed(plan.getDogBreed())
                .dogSize(plan.getDogSize())
                .memo(plan.getMemo())
                .build();
    }
}
