package com.study.usermanagementsystem.service;

import com.study.usermanagementsystem.common.exception.UserException;
import com.study.usermanagementsystem.common.jwt.JwtUtil;
import com.study.usermanagementsystem.common.response.UserStatusCode;
import com.study.usermanagementsystem.domain.User;
import com.study.usermanagementsystem.dto.request.LoginRequestDto;
import com.study.usermanagementsystem.dto.response.LoginResponseDto;
import com.study.usermanagementsystem.repository.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    private AuthService authService;
    private InMemoryUserRepository inMemoryUserRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        inMemoryUserRepository = new InMemoryUserRepository();
        passwordEncoder = new BCryptPasswordEncoder();
        authService = new AuthService(inMemoryUserRepository, passwordEncoder, jwtUtil);

        // 사용자 사전 등록
        User user = User.create("test", passwordEncoder.encode("test"), "닉네임");
        inMemoryUserRepository.save(user);
    }

    @Test
    @DisplayName("로그인 성공 시 토큰 반환")
    void login_success() {
        // given
        LoginRequestDto request = new LoginRequestDto("test", "test");

        // when
        LoginResponseDto response = authService.login(request);

        // then
        assertNotNull(response.getToken());
        assertTrue(response.getToken().startsWith("ey"));
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 아이디")
    void login_fail_user_not_found() {
        // given
        LoginRequestDto request = new LoginRequestDto("test123", "test");

        // when & then
        UserException exception = assertThrows(UserException.class, () -> authService.login(request));
        assertEquals(UserStatusCode.INVALID_CREDENTIALS.getMessage(), exception.getMessage());
    }


    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void login_fail_wrong_password() {
        // given
        LoginRequestDto request = new LoginRequestDto("test", "test123");

        // when & then
        UserException exception = assertThrows(UserException.class, () -> authService.login(request));
        assertEquals(UserStatusCode.INVALID_CREDENTIALS.getMessage(), exception.getMessage());
    }

}