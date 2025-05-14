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

    public void ban() {
        this.isBanned = true;
    }

    public void setId(long id) {
        this.Id = id;
    }

    public void grantAdminRole() {
        this.role = UserRole.ADMIN;
    }

    // 인메모리 마스터 계정 미리 생성
    public static User createMaster(String username, String password, String nickname) {
        return new User(Long.MAX_VALUE, username, password, nickname, UserRole.MASTER, false);
    }
}
