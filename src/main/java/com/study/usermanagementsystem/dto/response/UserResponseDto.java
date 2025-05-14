package com.study.usermanagementsystem.dto.response;

import com.study.usermanagementsystem.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UserResponseDto {

    private String username;

    private String nickname;

    private List<UserRoles> roles;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .roles(List.of(new UserRoles(user.getRole())))
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
