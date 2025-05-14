package com.study.usermanagementsystem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "로그인 응답 DTO")
public class LoginResponseDto {

    @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0In0.qN4q2P3Kqf...")
    private String token;

    public static LoginResponseDto from(String token) {
        return new LoginResponseDto(token);
    }
}