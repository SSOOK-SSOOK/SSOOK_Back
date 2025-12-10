package com.ssook.mvc.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssook.mvc.common.ApiResponse;
import com.ssook.mvc.dto.user.UserJoinRequest;
import com.ssook.mvc.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 API
    @PostMapping("/signup")
    public ApiResponse<Object> signup(@Valid @RequestBody UserJoinRequest request) {
        // 1. 서비스에 회원가입 요청
        userService.signup(request);
        
        // 2. 성공 메시지 반환 (나중에 ApiResponse로 변경 가능)
        return ApiResponse.createSuccess("회원 가입이 완료되었습니다.");
    }
}