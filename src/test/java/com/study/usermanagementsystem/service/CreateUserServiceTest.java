package com.study.usermanagementsystem.service;

import com.study.usermanagementsystem.common.exception.UserException;
import com.study.usermanagementsystem.common.response.UserStatusCode;
import com.study.usermanagementsystem.dto.request.SignUpRequestDto;
import com.study.usermanagementsystem.dto.response.SignUpResponseDto;
import com.study.usermanagementsystem.repository.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateUserServiceTest {

    private CreateUserService createUserService;

    @BeforeEach
    void setUp() {
        createUserService = new CreateUserService(new InMemoryUserRepository());
    }

    @Test
    @DisplayName("회원가입 성공")
    void register_success() {
        // given
        SignUpRequestDto request = new SignUpRequestDto("hyeri", "password123", "hyeri@example.com");

        // when
        SignUpResponseDto response = createUserService.register(request);

        // then
        assertThat(response.getLoginId()).isEqualTo("hyeri");
        assertThat(response.getEmail()).isEqualTo("hyeri@example.com");
    }

    @Test
    @DisplayName("회원가입 실패 - 아이디 중복")
    void register_fail_id_duplicate(){
        // given
        SignUpRequestDto request = new SignUpRequestDto("hyeri", "password123", "hyeri@example.com");
        createUserService.register(request);

        // when & then
        assertThatThrownBy(() ->
                createUserService.register(request)
        ).isInstanceOf(UserException.class)
                .hasMessage(UserStatusCode.USER_ALREADY_EXISTS.getMessage());
    }
}