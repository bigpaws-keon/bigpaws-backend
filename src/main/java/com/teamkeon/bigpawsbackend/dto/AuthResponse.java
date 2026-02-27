package com.teamkeon.bigpawsbackend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String token;
    private String email;
    private String nickname;
}
