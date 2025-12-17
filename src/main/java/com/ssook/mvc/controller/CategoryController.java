package com.ssook.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssook.mvc.common.ApiResponse;
import com.ssook.mvc.dto.category.response.CategoryResponseDto;
import com.ssook.mvc.dto.common.PageResponse;
import com.ssook.mvc.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<PageResponse<CategoryResponseDto>> getCategoryList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage, 
            HttpServletRequest request) {
        
        // 인터셉터가 넣어준 userId 꺼내기 (비로그인 시 null일 수 있음)
        Long userId = (Long) request.getAttribute("userId");
        
        PageResponse<CategoryResponseDto> response = categoryService.getCategoryList(page, perPage, userId);
        
        return ApiResponse.success(response);
    }
    
    @GetMapping("/{categoryId}")
    public ApiResponse<CategoryResponseDto> getCategoryDetail(
            @PathVariable Integer categoryId,
            HttpServletRequest request) {
        
        // 상세 조회에서도 '내가 구독했는지' 보여줘야 하므로 userId를 꺼냅니다.
        Long userId = (Long) request.getAttribute("userId");
        
        CategoryResponseDto response = categoryService.getCategoryDetail(categoryId, userId);
        
        return ApiResponse.success(response);
    }
}