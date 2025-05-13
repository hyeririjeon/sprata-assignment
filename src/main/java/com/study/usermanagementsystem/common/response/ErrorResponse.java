package com.study.usermanagementsystem.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ErrorResponse {

    private Error error;

    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class Error {
        private String code;
        private String message;
    }

    public static ErrorResponse of(StatusCode statusCode) {
        return new ErrorResponse(Error.of(statusCode.getName(), statusCode.getMessage()));
    }

}