package com.teamkeon.bigpawsbackend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VisitPlanResponse {
    private Long id;
    private String nickname;
    private Long placeId;
    private String placeName;
    private String visitDate;
    private String dogName;
    private String dogBreed;
    private String dogSize;
    private String memo;
}
