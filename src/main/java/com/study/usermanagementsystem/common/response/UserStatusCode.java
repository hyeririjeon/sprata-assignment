package com.study.usermanagementsystem.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserStatusCode implements StatusCode {

    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 가입된 사용자입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."),
    USER_IS_BANNED(HttpStatus.FORBIDDEN, "정지된 사용자입니다. 관리자에게 문의 바랍니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증 토큰입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
