package com.study.usermanagementsystem.common.response;

import org.springframework.http.HttpStatus;

public interface StatusCode {
    HttpStatus getHttpStatus();
    String getMessage();
    String getName();
}
