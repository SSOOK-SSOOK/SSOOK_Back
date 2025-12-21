package com.ssook.mvc.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // IllegalArgumentException이 발생하면 이 메서드가 실행됩니다.
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // HTTP 상태 코드를 400으로 설정
    public ApiResponse<Object> handleIllegalArgumentException(IllegalArgumentException e) {

        // 예외 메시지(e.getMessage())를 담아서 ApiResponse로 반환
        return new ApiResponse<>(400, e.getMessage(), null);
    }
}
