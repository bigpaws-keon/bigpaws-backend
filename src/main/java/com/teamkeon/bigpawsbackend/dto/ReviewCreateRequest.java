package com.teamkeon.bigpawsbackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateRequest {
    private String content;
    private int kindnessScore;
    private int sizeScore;
    private int bigDogScore;
}