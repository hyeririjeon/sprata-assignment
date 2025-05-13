package com.study.usermanagementsystem.common.exception;

import com.study.usermanagementsystem.common.response.CommonStatusCode;
import com.study.usermanagementsystem.common.response.ErrorResponse;
import com.study.usermanagementsystem.common.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        StatusCode code = e.getStatusCode();

        return ResponseEntity
                .status(code.getHttpStatus())
                .body(ErrorResponse.of(code));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
        log.error("Unhandled Exception", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(CommonStatusCode.INTERNAL_ERROR));
    }
}
