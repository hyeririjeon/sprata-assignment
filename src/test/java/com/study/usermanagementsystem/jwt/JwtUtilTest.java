package com.study.usermanagementsystem.jwt;

import com.study.usermanagementsystem.common.exception.UserException;
import com.study.usermanagementsystem.common.jwt.JwtUtil;
import com.study.usermanagementsystem.common.response.UserStatusCode;
import com.study.usermanagementsystem.dto.response.UserRole;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("토큰 생성 성공")
    void create_token_success() {
        String token = jwtUtil.createToken("admin", UserRole.ADMIN);

        assertNotNull(token);
        assertTrue(token.startsWith("Bearer "));
    }

    @Test
    @DisplayName("토큰에서 사용자 정보 추출")
    void get_user_info_from_token() {
        String token = jwtUtil.createToken("admin", UserRole.ADMIN);
        String pureToken = token.substring(7);

        Claims claims = jwtUtil.getUserInfoFromToken(pureToken);

        assertEquals("admin", claims.getSubject());
        assertEquals(UserRole.ADMIN.name(), claims.get("auth"));
    }

    @Test
    @DisplayName("유효한 토큰 검증 성공")
    void validate_token_success() {
        String token = jwtUtil.createToken("admin", UserRole.ADMIN);
        String pureToken = token.substring(7);

        boolean result = jwtUtil.validateToken(pureToken);

        assertTrue(result);
    }

    @Test
    @DisplayName("만료된 토큰 검증 실패")
    void validate_expired_token_fail() throws Exception {

        String token = jwtUtil.createToken("user", UserRole.USER, 1L);
        String pureToken = token.substring(7);

        Thread.sleep(1100);

        UserException exception = assertThrows(UserException.class, () -> {
            jwtUtil.validateToken(pureToken);
        });

        assertEquals(UserStatusCode.INVALID_TOKEN, exception.getStatusCode());
    }

    @Test
    @DisplayName("잘못된 형식의 토큰 검증 실패")
    void validate_invalid_token_fail() {
        String fakeToken = "asdfdsfdsafsdfsda";

        UserException exception = assertThrows(UserException.class, () -> {
            jwtUtil.validateToken(fakeToken);
        });

        assertEquals(UserStatusCode.INVALID_TOKEN, exception.getStatusCode());
    }
}
