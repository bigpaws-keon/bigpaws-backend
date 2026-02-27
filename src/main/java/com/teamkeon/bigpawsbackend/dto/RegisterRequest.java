package com.teamkeon.bigpawsbackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String nickname;
}
