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
            @RequestParam(required = false) String keyword,
            HttpServletRequest request) {

        // 인터셉터가 넣어준 userId 꺼내기 (비로그인 시 null일 수 있음)
        Long userId = (Long) request.getAttribute("userId");

        PageResponse<CategoryResponseDto> response = categoryService.getCategoryList(page, perPage, userId, keyword);

        return ApiResponse.success(response);
    }

    @GetMapping("/{categoryId}")
    public ApiResponse<Object> getCategoryDetail(
            @PathVariable Integer categoryId,
            HttpServletRequest request) {

        try {
            Long userId = (Long) request.getAttribute("userId");
            CategoryResponseDto response = categoryService.getCategoryDetail(categoryId, userId);

            return ApiResponse.success(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(500, e.toString(), null);
        }
    }
}