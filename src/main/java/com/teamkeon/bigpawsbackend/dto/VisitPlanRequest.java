package com.teamkeon.bigpawsbackend.dto;

import lombok.Getter;

@Getter
public class VisitPlanRequest {
    private String visitDate; // "2025-03-15T14:00"
    private String dogName;
    private String dogBreed;
    private String dogSize; // SMALL, MEDIUM, LARGE
    private String memo;
}
