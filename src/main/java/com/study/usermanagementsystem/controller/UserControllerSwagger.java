package com.study.usermanagementsystem.controller;

import com.study.usermanagementsystem.common.security.UserDetailsImpl;
import com.study.usermanagementsystem.dto.request.SignUpRequestDto;
import com.study.usermanagementsystem.dto.response.AdminUserResponseDto;
import com.study.usermanagementsystem.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserControllerSwagger {

    @Operation(summary = "회원가입", description = "신규 사용자를 등록합니다.",
            responses = @ApiResponse(responseCode = "201", description = "회원가입 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class),
                            examples = @ExampleObject(
                                    name = "회원가입 성공 예시",
                                    value = """
                    {
                      "username": "JIN HO",
                      "nickname": "Mentos",
                      "roles": [
                        { "role": "USER" }
                      ]
                    }
                    """
                            )
                    )
            )
    )
    @PostMapping("/signup")
    ResponseEntity<UserResponseDto> register(@RequestBody @Valid SignUpRequestDto requestDto);

    @Operation(summary = "내 정보 조회", description = "사용자 자신의 정보를 조회합니다.",
            responses = @ApiResponse(responseCode = "200", description = "내 정보 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class),
                            examples = @ExampleObject(
                                    name = "내 정보 조회 예시",
                                    value = """
                        {
                          "username": "JIN HO",
                          "nickname": "Mentos",
                          "roles": [
                            { "role": "USER" }
                          ]
                        }
                    """
                            )
                    )
            )
    )
    @GetMapping("/myInfo")
    ResponseEntity<UserResponseDto> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(summary = "관리자 권한 부여", description = "MASTER 권한으로 특정 사용자에게 ADMIN 권한을 부여합니다.",
            responses = @ApiResponse(responseCode = "200", description = "권한 부여 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class),
                            examples = @ExampleObject(
                                    name = "권한 부여 성공 예시",
                                    value = """
                        {
                          "username": "JIN HO",
                          "nickname": "Mentos",
                          "roles": [
                            { "role": "ADMIN" }
                          ]
                        }
                    """
                            )
                    )
            )
    )
    @PatchMapping("/admin/users/{username}/roles")
    ResponseEntity<UserResponseDto> grantAdminRole(@PathVariable String username);

    @Operation(summary = "사용자 전체 조회", description = "관리자 권한으로 전체 사용자 목록을 조회합니다.",
            responses = @ApiResponse(responseCode = "200", description = "전체 사용자 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdminUserResponseDto.class),
                            examples = @ExampleObject(
                                    name = "사용자 목록 조회 예시",
                                    value = """
                        [
                          {
                            "username": "user1",
                            "nickname": "닉네임1",
                            "roles": [{ "role": "USER" }],
                            "banned": false
                          },
                          {
                            "username": "admin",
                            "nickname": "관리자",
                            "roles": [{ "role": "ADMIN" }],
                            "banned": false
                          }
                        ]
                    """
                            )
                    )
            )
    )
    @GetMapping("/admin/users")
    ResponseEntity<List<AdminUserResponseDto>> getUsers();

    @Operation(summary = "사용자 정지", description = "관리자 권한으로 특정 사용자를 정지 처리합니다.",
            responses = @ApiResponse(responseCode = "200", description = "정지 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdminUserResponseDto.class),
                            examples = @ExampleObject(
                                    name = "사용자 정지 성공 예시",
                                    value = """
                        {
                          "username": "user123",
                          "nickname": "닉네임",
                          "roles": [
                            { "role": "USER" }
                          ],
                          "banned": true
                        }
                    """
                            )
                    )
            )
    )
    @PatchMapping("/admin/users/{username}/ban")
    ResponseEntity<AdminUserResponseDto> banUser(@PathVariable String username);
}