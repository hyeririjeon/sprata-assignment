package com.study.usermanagementsystem.service;

import com.study.usermanagementsystem.common.exception.UserException;
import com.study.usermanagementsystem.common.response.UserStatusCode;
import com.study.usermanagementsystem.domain.User;
import com.study.usermanagementsystem.dto.request.SignUpRequestDto;
import com.study.usermanagementsystem.dto.response.UserResponseDto;
import com.study.usermanagementsystem.repository.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserCreationServiceTest {

    private UserCreationService userCreationService;
    private InMemoryUserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        passwordEncoder = new BCryptPasswordEncoder();
        userCreationService = new UserCreationService(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 성공")
    void register_success() {
        // given
        SignUpRequestDto request = new SignUpRequestDto("test", "password123", "test");

        // when
        UserResponseDto response = userCreationService.register(request);

        // then
        assertEquals("test", response.getUsername());
        assertEquals("test", response.getNickname());
    }

    @Test
    @DisplayName("회원가입 실패 - 아이디 중복")
    void register_fail_id_duplicate() {
        // given
        SignUpRequestDto request = new SignUpRequestDto("test", "password123", "test");
        userCreationService.register(request);

        // when & then
        UserException exception = assertThrows(UserException.class, () -> userCreationService.register(request));
        assertEquals(UserStatusCode.USER_ALREADY_EXISTS.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("비밀번호 암호화 확인")
    void password_encode() {
        // given
        SignUpRequestDto request = new SignUpRequestDto("test", "1234", "test");
        userCreationService.register(request);

        // when
        User savedUser = userRepository.findByLoginId("test").orElseThrow();

        // then
        assertNotEquals("1234", savedUser.getPassword());
        assertTrue(passwordEncoder.matches("1234", savedUser.getPassword()));
    }
}
