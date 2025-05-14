package com.study.usermanagementsystem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "로그인 요청 DTO")
public class LoginRequestDto {

    @NotNull(message = "아이디를 입력해주세요.")
    @Schema(description = "로그인 아이디", example = "testuser")
    private String username;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Schema(description = "로그인 비밀번호", example = "securePassword123!")
    private String password;
}
