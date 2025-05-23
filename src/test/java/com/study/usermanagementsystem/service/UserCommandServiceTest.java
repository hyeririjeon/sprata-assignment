package com.study.usermanagementsystem.service;

import com.study.usermanagementsystem.common.exception.UserException;
import com.study.usermanagementsystem.common.response.UserStatusCode;
import com.study.usermanagementsystem.domain.User;
import com.study.usermanagementsystem.dto.response.UserRole;
import com.study.usermanagementsystem.repository.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserCommandServiceTest {

    private UserCommandService userCommandService;
    private InMemoryUserRepository inMemoryUserRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        inMemoryUserRepository = new InMemoryUserRepository();
        userCommandService = new UserCommandService(inMemoryUserRepository);
        passwordEncoder = new BCryptPasswordEncoder();

        // given - 기본 사용자 등록
        User user = User.create("test", passwordEncoder.encode("test"), "test");
        inMemoryUserRepository.save(user);
    }

    @Test
    @DisplayName("관리자 권한 부여 성공")
    void grant_admin_role_success() {
        // when - 관리자 권한 부여
        userCommandService.grantAdminRole("test");

        // then - 권한이 ADMIN으로 변경되었는지 확인
        User changeUser = inMemoryUserRepository.findByLoginId("test").orElseThrow();
        assertEquals(UserRole.ADMIN, changeUser.getRole());
    }

    @Test
    @DisplayName("관리자 권한 부여 실패 - 존재하지 않는 사용자")
    void grant_admin_role_fail_by_no_user() {
        // when & then - 예외 발생 확인
        UserException exception = assertThrows(UserException.class, () -> {
            userCommandService.grantAdminRole("test1");
        });

        assertEquals(UserStatusCode.USER_NOT_FOUND, exception.getStatusCode());
    }
}
