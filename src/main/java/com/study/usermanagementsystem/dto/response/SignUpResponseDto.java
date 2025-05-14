package com.study.usermanagementsystem.dto.response;

import com.study.usermanagementsystem.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class SignUpResponseDto {

    private String username;

    private String password;

    private String nickname;

    private UserRole role;

    public static SignUpResponseDto from(User user) {
        return SignUpResponseDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .build();
    }
}
