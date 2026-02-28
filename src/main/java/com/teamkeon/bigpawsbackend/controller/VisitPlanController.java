package com.teamkeon.bigpawsbackend.controller;

import com.teamkeon.bigpawsbackend.dto.VisitPlanRequest;
import com.teamkeon.bigpawsbackend.dto.VisitPlanResponse;
import com.teamkeon.bigpawsbackend.service.VisitPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VisitPlanController {
    private final VisitPlanService visitPlanService;

    @PostMapping("/api/places/{placeId}/visits")
    public VisitPlanResponse createVisitPlan(@PathVariable Long placeId, @RequestBody VisitPlanRequest request) {
        return visitPlanService.create(placeId, request);
    }

    @GetMapping("/api/places/{placeId}/visits")
    public List<VisitPlanResponse> getVisitPlans(@PathVariable Long placeId) {
        return visitPlanService.getByPlace(placeId);
    }

    @GetMapping("/api/my/visits")
    public List<VisitPlanResponse> getMyVisitPlans() {
        return visitPlanService.getMyPlans();
    }

    @DeleteMapping("/api/visits/{planId}")
    public void deleteVisitPlan(@PathVariable Long planId) {
        visitPlanService.delete(planId);
    }
}
