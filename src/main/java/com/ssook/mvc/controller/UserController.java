package com.ssook.mvc.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssook.mvc.common.ApiResponse;
import com.ssook.mvc.dto.user.UserJoinRequest;
import com.ssook.mvc.dto.user.UserLoginRequest;
import com.ssook.mvc.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
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
    
    // 로그인 API
    @PostMapping("/login")
    public ApiResponse<Object> login(@RequestBody UserLoginRequest request, HttpServletResponse response) {
        // 1. 서비스에서 토큰 받아오기
        String token = userService.login(request);

        // 2. API 명세서대로 헤더에 'token' 추가
        response.setHeader("token", token);

        // 3. 바디에는 성공 메시지만 반환 (data: [])
        return ApiResponse.createSuccess("로그인에 성공했습니다.");
    }
}