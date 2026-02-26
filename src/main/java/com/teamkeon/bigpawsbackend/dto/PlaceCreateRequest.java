package com.teamkeon.bigpawsbackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceCreateRequest {
    private String name;
    private String description;
}