package com.teamkeon.bigpawsbackend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceResponse {
    private Long id;
    private String name;
    private String description;
    private double avgKindnessScore;
    private double avgSizeScore;
    private double avgBigDogScore;
    private int reviewCount;
}
