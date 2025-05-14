package com.study.usermanagementsystem.service;

import com.study.usermanagementsystem.common.exception.UserException;
import com.study.usermanagementsystem.common.response.UserStatusCode;
import com.study.usermanagementsystem.domain.User;
import com.study.usermanagementsystem.dto.request.SignUpRequestDto;
import com.study.usermanagementsystem.dto.response.SignUpResponseDto;
import com.study.usermanagementsystem.repository.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateUserServiceTest {

    private CreateUserService createUserService;
    private InMemoryUserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        passwordEncoder = new BCryptPasswordEncoder();
        createUserService = new CreateUserService(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 성공")
    void register_success() {
        // given
        SignUpRequestDto request = new SignUpRequestDto("test", "password123", "test");

        // when
        SignUpResponseDto response = createUserService.register(request);

        // then
        assertThat(response.getUsername()).isEqualTo("test");
        assertThat(response.getNickname()).isEqualTo("test");
    }

    @Test
    @DisplayName("회원가입 실패 - 아이디 중복")
    void register_fail_id_duplicate(){
        // given
        SignUpRequestDto request = new SignUpRequestDto("test", "password123", "test");
        createUserService.register(request);

        // when & then
        assertThatThrownBy(() ->
                createUserService.register(request)
        ).isInstanceOf(UserException.class)
                .hasMessage(UserStatusCode.USER_ALREADY_EXISTS.getMessage());
    }

    @Test
    @DisplayName("비밀번호 암호화 확인")
    void password_should_be_encoded() {
        // given
        SignUpRequestDto request = new SignUpRequestDto("test", "1234", "test");
        createUserService.register(request);

        // when
        User savedUser = userRepository.findByLoginId("test").orElseThrow();

        // then
        assertThat(savedUser.getPassword()).isNotEqualTo("1234");
        assertThat(passwordEncoder.matches("1234", savedUser.getPassword())).isTrue();
    }

}