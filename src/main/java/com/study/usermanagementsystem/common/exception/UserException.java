package com.study.usermanagementsystem.common.exception;

import com.study.usermanagementsystem.common.response.StatusCode;

public class UserException extends RuntimeException {
    private final StatusCode statusCode;

    public UserException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
