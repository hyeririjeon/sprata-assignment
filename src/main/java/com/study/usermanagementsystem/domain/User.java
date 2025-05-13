package com.study.usermanagementsystem.domain;

import com.study.usermanagementsystem.dto.response.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class User {

    private Long Id;

    private String loginId;

    private String password;

    private String email;

    @Builder.Default
    private UserRole role = UserRole.MEMBER;

    @Builder.Default
    private boolean isBanned = false;


    public static User create(String loginId, String password, String email) {
        return User.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .build();
    }

    public void setId(long id) {
        this.Id = id;
    }
}
