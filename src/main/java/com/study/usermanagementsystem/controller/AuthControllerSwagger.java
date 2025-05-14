package com.study.usermanagementsystem.controller;

import com.study.usermanagementsystem.dto.request.LoginRequestDto;
import com.study.usermanagementsystem.dto.response.LoginResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@Tag(name = "Auth", description = "인증 관련 API")
public interface AuthControllerSwagger {

    @Operation(summary = "로그인", description = "아이디와 비밀번호를 통해 JWT를 발급받습니다.")
    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto requestDto);
}
