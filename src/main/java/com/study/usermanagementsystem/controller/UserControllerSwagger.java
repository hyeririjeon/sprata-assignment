package com.study.usermanagementsystem.controller;

import com.study.usermanagementsystem.common.security.UserDetailsImpl;
import com.study.usermanagementsystem.dto.request.SignUpRequestDto;
import com.study.usermanagementsystem.dto.response.AdminUserResponseDto;
import com.study.usermanagementsystem.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "사용자 관련 API")
public interface UserControllerSwagger {

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/signup")
    ResponseEntity<UserResponseDto> register(@RequestBody @Valid SignUpRequestDto requestDto);

    @Operation(summary = "내 정보 조회", description = "사용자 자신의 정보를 조회합니다.")
    @GetMapping("/myInfo")
    ResponseEntity<UserResponseDto> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "관리자 권한 부여", description = "MASTER 권한으로 특정 사용자에게 ADMIN 권한을 부여합니다.")
    @PatchMapping("/admin/users/{username}/roles")
    ResponseEntity<UserResponseDto> grantAdminRole(@PathVariable String username);

    @Operation(summary = "사용자 전체 조회", description = "관리자 권한으로 전체 사용자 목록을 조회합니다.")
    @GetMapping("/admin/users")
    ResponseEntity<List<AdminUserResponseDto>> getUsers();

    @Operation(summary = "사용자 정지", description = "관리자 권한으로 특정 사용자를 정지 처리합니다.")
    @PatchMapping("/admin/users/{username}/ban")
    ResponseEntity<AdminUserResponseDto> banUser(@PathVariable String username);
}