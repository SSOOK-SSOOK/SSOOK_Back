package com.ssook.mvc.controller;

import com.ssook.mvc.common.ApiResponse;
import com.ssook.mvc.service.SubscriptionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    // 구독하기
    @PostMapping("/{categoryId}")
    public ApiResponse<Object> subscribe(@PathVariable Integer categoryId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        
        subscriptionService.subscribe(userId, categoryId);
        
        return ApiResponse.createSuccess("카테고리를 구독했습니다.");
    }

    // 구독 취소하기
    @DeleteMapping("/{categoryId}")
    public ApiResponse<Object> unsubscribe(@PathVariable Integer categoryId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        
        subscriptionService.unsubscribe(userId, categoryId);
        
        return ApiResponse.createSuccess("구독을 취소했습니다.");
    }
}