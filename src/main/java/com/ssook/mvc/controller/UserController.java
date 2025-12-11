package com.ssook.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssook.mvc.common.ApiResponse;
import com.ssook.mvc.dto.user.UserInfoResponse;
import com.ssook.mvc.dto.user.UserJoinRequest;
import com.ssook.mvc.dto.user.UserLoginRequest;
import com.ssook.mvc.dto.user.UserModifyRequest;
import com.ssook.mvc.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
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
        // 서비스에 회원가입 요청
        userService.signup(request);
        
        // 성공 메시지 반환 (나중에 ApiResponse로 변경 가능)
        return ApiResponse.createSuccess("회원 가입이 완료되었습니다.");
    }
    
    // 로그인 API
    @PostMapping("/login")
    public ApiResponse<Object> login(@RequestBody UserLoginRequest request, HttpServletResponse response) {
        // 서비스에서 토큰 받아오기
        String token = userService.login(request);

        // API 명세서대로 헤더에 'token' 추가
        response.setHeader("token", token);

        // 바디에는 성공 메시지만 반환 (data: [])
        return ApiResponse.createSuccess("로그인에 성공했습니다.");
    }
    
    // 내 정보 조회 API
    @GetMapping("/me")
    public ApiResponse<UserInfoResponse> getMyInfo(HttpServletRequest request) {
        // Interceptor가 넣어둔 "userId"
        Long userId = (Long) request.getAttribute("userId");
        
        // 서비스 호출
        UserInfoResponse userInfo = userService.getMyInfo(userId);
        
        // 응답 (데이터가 담긴 성공 응답)
        return ApiResponse.success(userInfo);
    }
    
 // 내 정보 수정 API
    @PatchMapping("/me")
    public ApiResponse<UserInfoResponse> modifyUser(
            @Valid @RequestBody UserModifyRequest request, 
            HttpServletRequest httpRequest) { // 변수명 겹칠까봐 httpRequest로 씀
        
        Long userId = (Long) httpRequest.getAttribute("userId");
        
        UserInfoResponse updatedInfo = userService.modifyUser(userId, request);
        
        return ApiResponse.success(updatedInfo);
    }
    
}