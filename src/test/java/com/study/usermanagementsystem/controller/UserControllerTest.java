package com.study.usermanagementsystem.controller;

import com.study.usermanagementsystem.common.jwt.JwtUtil;
import com.study.usermanagementsystem.domain.User;
import com.study.usermanagementsystem.dto.response.UserRole;
import com.study.usermanagementsystem.repository.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private InMemoryUserRepository inMemoryUserRepository;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public InMemoryUserRepository userRepository() {
            return new InMemoryUserRepository();
        }
    }

    @BeforeEach
    void setUp() {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 사용자 사전 등록 - 일반
        User user = User.create("user", passwordEncoder.encode("test"), "test");
        // 사용자 사전 등록 - 마스터
        User master = User.createMaster("master", passwordEncoder.encode("test"), "test");

        inMemoryUserRepository.save(user);
        inMemoryUserRepository.save(master);

    }

    @Test
    @DisplayName("관리자 권한 부여 성공")
    void grant_admin_role__role_success() throws Exception {
        String token = jwtUtil.createToken("master", UserRole.MASTER);
        mockMvc.perform(patch("/admin/users/{username}/roles", "user")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("관리자 권한 부여 실패 - 올바르지 않은 토큰")
    void grant_admin_role_fail_by_token() throws Exception {
        String token = jwtUtil.createToken("master", UserRole.MASTER);
        mockMvc.perform(patch("/admin/users/{username}/roles", "user")
                        .header("Authorization", token+"a"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("관리자 권한 부여 실패 - USER")
    void grant_admin_role_fail_by_role() throws Exception {
        String token = jwtUtil.createToken("user", UserRole.USER);

        mockMvc.perform(patch("/admin/users/{username}/roles", "user")
                        .header("Authorization", token))
                .andExpect(status().isForbidden());
    }

}
