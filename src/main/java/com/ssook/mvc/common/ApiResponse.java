package com.ssook.mvc.common;

import java.util.Collections;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private int status;       // 201, 200, 400 등등
    private String message;   // "회원 가입이 완료되었습니다."
    private T data;

    // [데이터 포함] 조회 성공 시 사용 (예: 내 정보 조회, 영상 목록)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "요청이 성공했습니다.", data);
    }

    // [메시지 중심] 생성/수정/삭제 성공 시 사용 (예: 회원가입, 로그아웃)
    public static <T> ApiResponse<T> createSuccess(String message) {
        // 데이터 자리에 빈 리스트(Collections.emptyList())를 넣어서 []로 보냄
        return new ApiResponse<>(201, message, (T) Collections.emptyList());
    }
}