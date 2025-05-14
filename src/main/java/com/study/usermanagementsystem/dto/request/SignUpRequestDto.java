package com.study.usermanagementsystem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "회원가입 요청 DTO")
public class SignUpRequestDto {

    @NotNull(message = "아이디를 입력해주세요.")
    @Schema(description = "회원가입 아이디", example = "newuser")
    private String username;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Schema(description = "회원가입 비밀번호", example = "NewUser123!")
    private String password;

    @NotNull(message = "닉네임을 입력해주세요.")
    @Schema(description = "회원가입 닉네임", example = "홍길동")
    private String nickname;
}
