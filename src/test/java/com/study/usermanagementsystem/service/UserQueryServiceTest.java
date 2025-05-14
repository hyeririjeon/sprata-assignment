package com.study.usermanagementsystem.service;

import com.study.usermanagementsystem.domain.User;
import com.study.usermanagementsystem.dto.response.UserResponseDto;
import com.study.usermanagementsystem.dto.response.UserRole;
import com.study.usermanagementsystem.repository.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UserQueryServiceTest {

    private UserQueryService userQueryService;
    private InMemoryUserRepository inMemoryUserRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        inMemoryUserRepository = new InMemoryUserRepository();
        userQueryService = new UserQueryService(inMemoryUserRepository);
        passwordEncoder = new BCryptPasswordEncoder();

        // 사용자 사전 등록 - 일반
        User user = User.create("test", passwordEncoder.encode("test"), "test");
        inMemoryUserRepository.save(user);

    }

    @Test
    @DisplayName("자신의 정보 조회 성공")
    void get_my_info_success() {

        UserResponseDto responseDto = userQueryService.getUser("test");

        assertEquals("test", responseDto.getUsername());
        assertEquals("test", responseDto.getNickname());

    }



}