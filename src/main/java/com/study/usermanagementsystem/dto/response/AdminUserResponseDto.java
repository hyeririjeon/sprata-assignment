package com.study.usermanagementsystem.dto.response;

import com.study.usermanagementsystem.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "관리자용 사용자 응답 DTO")
public class AdminUserResponseDto {

    @Schema(description = "사용자 아이디", example = "testuser")
    private String username;

    @Schema(description = "사용자 닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "사용자 권한 목록", example = "[{\"role\": \"USER\"}]")
    private List<UserRoles> roles;

    @Schema(description = "정지 여부", example = "false")
    private boolean isBanned;

    public static AdminUserResponseDto from(User user) {
        return AdminUserResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .roles(List.of(new UserRoles(user.getRole())))
                .isBanned(user.isBanned())
                .build();
    }

    @Getter
    @Schema(description = "권한 정보")
    public static class UserRoles {

        @Schema(description = "권한 이름", example = "USER")
        private final String role;

        public UserRoles(UserRole role) {
            this.role = role.name();
        }
    }
}