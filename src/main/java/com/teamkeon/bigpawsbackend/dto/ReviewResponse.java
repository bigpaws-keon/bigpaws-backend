package com.teamkeon.bigpawsbackend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {
    private Long id;
    private String content;
    private int kindnessScore;
    private int sizeScore;
    private int bigDogScore;
    private String nickname;
}
