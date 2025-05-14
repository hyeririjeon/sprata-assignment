package com.study.usermanagementsystem.dto.response;

import com.study.usermanagementsystem.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class AdminUserResponseDto {

    private String username;
    private String nickname;
    private List<UserRoles> roles;
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
    private static class UserRoles {
        private final String role;

        public UserRoles(UserRole role) {
            this.role = role.name();
        }
    }
}
