package com.study.usermanagementsystem.service;

import com.study.usermanagementsystem.domain.User;
import com.study.usermanagementsystem.dto.response.AdminUserResponseDto;
import com.study.usermanagementsystem.dto.response.UserResponseDto;
import com.study.usermanagementsystem.repository.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserQueryServiceTest {

    private UserQueryService userQueryService;
    private InMemoryUserRepository inMemoryUserRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {

        inMemoryUserRepository = new InMemoryUserRepository();
        userQueryService = new UserQueryService(inMemoryUserRepository);
        passwordEncoder = new BCryptPasswordEncoder();

        // given - 사용자 사전 등록 (기본 유저)
        User user = User.create("test", passwordEncoder.encode("test"), "test");
        inMemoryUserRepository.save(user);

    }

    @Test
    @DisplayName("자신의 정보 조회 성공")
    void get_my_info_success() {
        // when - 사용자 정보 조회
        UserResponseDto responseDto = userQueryService.getUser("test");

        // then - 사용자 정보 확인
        assertEquals("test", responseDto.getUsername());
        assertEquals("test", responseDto.getNickname());

    }

    @Test
    @DisplayName("관리자가 사용자 전체 조회 성공")
    void get_users_info_success() {
        // given - 사용자 추가 등록
        User user = User.create("test2", passwordEncoder.encode("test"), "test");
        inMemoryUserRepository.save(user);

        // when - 전체 사용자 조회
        List<AdminUserResponseDto> responseDto = userQueryService.getUsers();

        // then - 전체 사용자 수 및 특정 사용자 포함 여부 확인
        assertEquals(2, responseDto.size());

        boolean containsTest2 = responseDto.stream()
                .anyMatch(dto -> dto.getUsername().equals("test2"));
        assertTrue(containsTest2);

    }
}
