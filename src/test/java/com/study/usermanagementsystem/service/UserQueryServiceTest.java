package com.study.usermanagementsystem.service;

import com.study.usermanagementsystem.common.exception.UserException;
import com.study.usermanagementsystem.common.response.UserStatusCode;
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
    @DisplayName("사용자 전체 조회 성공")
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

    @Test
    @DisplayName("사용자 정지 성공")
    void ban_user_success() {
        // given - 기존 사용자 등록 및 상태 확인
        User testUser = inMemoryUserRepository.findByLoginId("test").orElseThrow();
        assertFalse(testUser.isBanned()); // 정지 전 상태 확인

        // when - 사용자 정지 수행
        userQueryService.banUser("test");

        // then - 정지 상태로 변경되었는지 확인
        assertTrue(testUser.isBanned());

        // then - 이미 정지된 사용자에게 다시 정지 시도 시 예외 발생
        UserException exception = assertThrows(UserException.class, () -> userQueryService.banUser("test"));
        assertEquals(UserStatusCode.USER_ALREADY_BANNED, exception.getStatusCode());

        // then - 정지된 사용자가 본인 정보 조회 시 예외 발생
        UserException exception2 = assertThrows(UserException.class, () -> userQueryService.getUser("test"));
        assertEquals(UserStatusCode.USER_IS_BANNED, exception2.getStatusCode());
    }

}
