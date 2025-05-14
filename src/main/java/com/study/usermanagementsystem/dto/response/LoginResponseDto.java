package com.study.usermanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponseDto {

    private String token;

    public static LoginResponseDto from(String token) {
        return new LoginResponseDto(token);
    }
}
