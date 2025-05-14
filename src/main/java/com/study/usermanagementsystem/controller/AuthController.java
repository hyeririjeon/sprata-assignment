package com.study.usermanagementsystem.controller;

import com.study.usermanagementsystem.dto.request.LoginRequestDto;
import com.study.usermanagementsystem.dto.response.LoginResponseDto;
import com.study.usermanagementsystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto requestDto) {
        LoginResponseDto responseDto = authService.login(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
