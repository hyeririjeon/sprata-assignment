package com.study.usermanagementsystem.domain;

import com.study.usermanagementsystem.dto.response.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class User {

    private Long Id;

    private String username;

    private String password;

    private String nickname;

    @Builder.Default
    private UserRole role = UserRole.USER;

    @Builder.Default
    private boolean isBanned = false;


    public static User create(String username, String password, String nickname) {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();
    }

    public void setId(long id) {
        this.Id = id;
    }
}
