package com.ssook.mvc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssook.mvc.dto.category.response.CategoryResponseDto;
import com.ssook.mvc.dto.common.PageResponse;
import com.ssook.mvc.entity.CategoryEntity;
import com.ssook.mvc.repository.CategoryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public PageResponse<CategoryResponseDto> getCategoryList(int page, int size, Long userId) {
        // 페이징 계산 (offset = (페이지-1) * 개수)
        int offset = (page - 1) * size;

        // DB 조회
        List<CategoryEntity> entities = categoryMapper.selectCategoryList(offset, size);
        int totalCount = categoryMapper.countCategory();

        // Entity -> DTO 변환
        List<CategoryResponseDto> content = entities.stream()
                .map(CategoryResponseDto::from)
                .collect(Collectors.toList());

        // 추후 Subscription 기능 구현 시, userId로 구독 여부 체크 로직 추가 예정
        if (userId != null) {
            // 여기에 로직 들어갈 자리
        }

        // 공통 페이징 객체에 담아 반환
        return new PageResponse<>(content, page, size, totalCount);
    }
}