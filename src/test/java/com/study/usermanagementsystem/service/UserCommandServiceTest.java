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

        // 사용자 사전 등록 - 일반
        User user = User.create("test", passwordEncoder.encode("test"), "test");
        inMemoryUserRepository.save(user);

    }

    @Test
    @DisplayName("관리자 권한 부여 성공")
    void grant_admin_role_success() {

        userCommandService.grantAdminRole("test");

        User changeUser = inMemoryUserRepository.findByLoginId("test").orElseThrow();

        assertEquals(UserRole.ADMIN, changeUser.getRole());

    }

    @Test
    @DisplayName("관리자 권한 부여 실패 - 존재하지 않는 사용자 ")
    void grant_admin_role_fail_by_no_user() {

        UserException exception = assertThrows(UserException.class, () -> userCommandService.grantAdminRole("test1"));

        assertEquals(UserStatusCode.USER_NOT_FOUND, exception.getStatusCode());

    }

}