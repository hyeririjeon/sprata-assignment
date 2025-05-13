package com.study.usermanagementsystem.dto.response;

import com.study.usermanagementsystem.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class SignUpResponseDto {

    private String loginId;

    private String password;

    private String email;

    private UserRole role;

    public static SignUpResponseDto from(User user) {
        return SignUpResponseDto.builder()
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }
}
