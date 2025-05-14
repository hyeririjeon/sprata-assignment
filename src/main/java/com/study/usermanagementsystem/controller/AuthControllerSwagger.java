package com.study.usermanagementsystem.controller;

import com.study.usermanagementsystem.dto.request.LoginRequestDto;
import com.study.usermanagementsystem.dto.response.LoginResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerSwagger {

    @Operation(summary = "로그인", description = "사용자가 로그인하면 JWT 토큰을 발급합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "로그인 성공 예시",
                                            value = """
                            {
                              "token": "eyJhbGciOiJIUzI1NiJ9..."
                            }
                        """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호 불일치",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "로그인 실패 예시 - 자격증명 오류",
                                            value = """
                            {
                              "error": {
                                "code": "INVALID_CREDENTIALS",
                                "message": "아이디 또는 비밀번호가 올바르지 않습니다."
                              }
                            }
                        """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "정지된 사용자",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "로그인 실패 예시 - 정지된 사용자",
                                            value = """
                            {
                              "error": {
                                "code": "USER_IS_BANNED",
                                "message": "정지된 사용자입니다. 관리자에게 문의 바랍니다."
                              }
                            }
                        """
                                    )
                            )
                    )
            })
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto);
}