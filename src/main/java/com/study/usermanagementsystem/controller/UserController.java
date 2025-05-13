package com.study.usermanagementsystem.controller;

import com.study.usermanagementsystem.dto.request.SignUpRequestDto;
import com.study.usermanagementsystem.dto.response.SignUpResponseDto;
import com.study.usermanagementsystem.service.CreateUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CreateUserService createUserService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> register(@Valid SignUpRequestDto requestDto) {
        SignUpResponseDto responseDto = createUserService.register(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
