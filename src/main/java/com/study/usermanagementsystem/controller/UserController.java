package com.study.usermanagementsystem.controller;

import com.study.usermanagementsystem.dto.request.SignUpRequestDto;
import com.study.usermanagementsystem.dto.response.UserResponseDto;
import com.study.usermanagementsystem.service.UserCommandService;
import com.study.usermanagementsystem.service.UserCreationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserCreationService userCreationService;
    private final UserCommandService userCommandService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid SignUpRequestDto requestDto) {
        UserResponseDto responseDto  = userCreationService.register(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PreAuthorize("hasRole('MASTER')")
    @PatchMapping("/admin/users/{username}/roles")
    public ResponseEntity<UserResponseDto> grantAdminRole(@PathVariable String username) {
        UserResponseDto responseDto = userCommandService.grantAdminRole(username);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
