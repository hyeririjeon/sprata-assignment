package com.study.usermanagementsystem.dto.response;

import com.study.usermanagementsystem.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@Schema(description = "일반 사용자 정보 응답 DTO")
public class UserResponseDto {

    @Schema(description = "사용자 아이디", example = "testuser")
    private String username;

    @Schema(description = "사용자 닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "사용자 권한 목록", example = "[{\"role\": \"USER\"}]")
    private List<UserRoles> roles;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .roles(List.of(new UserRoles(user.getRole())))
                .build();
    }

    @Getter
    @Schema(description = "사용자 권한 정보")
    public static class UserRoles {

        @Schema(description = "권한 이름", example = "USER", allowableValues = {"USER", "ADMIN", "MASTER"})
        private final String role;

        public UserRoles(UserRole role) {
            this.role = role.name();
        }
    }
}