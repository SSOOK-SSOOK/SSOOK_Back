package com.ssook.mvc.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssook.mvc.dto.category.response.CategoryResponseDto;
import com.ssook.mvc.dto.common.PageResponse;
import com.ssook.mvc.entity.CategoryEntity;
import com.ssook.mvc.repository.CategoryMapper;
import com.ssook.mvc.repository.SubscriptionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final SubscriptionMapper subscriptionMapper;

    @Transactional(readOnly = true)
    public PageResponse<CategoryResponseDto> getCategoryList(int page, int size, Long userId, String keyword) {
        // 페이징 계산 (offset = (페이지-1) * 개수)
        int offset = (page - 1) * size;

        // DB 조회
        List<CategoryEntity> entities = categoryMapper.selectCategoryList(offset, size, keyword);
        int totalCount = categoryMapper.countCategory(keyword);

        // Entity -> DTO 변환
        List<CategoryResponseDto> content = entities.stream()
                .map(CategoryResponseDto::from)
                .collect(Collectors.toList());

        // 구독 여부 체크 로직
        if (userId != null && !content.isEmpty()) {
            // 내가 구독한 카테고리 ID 목록을 가져옴 (예: [1, 3, 5])
            List<Integer> subscribedIds = subscriptionMapper.selectSubscribedCategoryIds(userId);

            // 비교하기 쉽게 Set으로 변환 (검색 속도 O(1))
            // import java.util.Set; import java.util.HashSet;
            Set<Integer> subscribedSet = new HashSet<>(subscribedIds);

            // 리스트를 돌면서 ID가 Set에 있으면 isSubscribed = true
            for (CategoryResponseDto dto : content) {
                if (subscribedSet.contains(dto.getCategoryId())) {
                    dto.setIsSubscribed(true);
                }
            }
        }

        // 공통 페이징 객체에 담아 반환
        return new PageResponse<>(content, page, size, totalCount);
    }

    // 카테고리 상세 조회
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryDetail(Integer categoryId, Long userId) {
        // DB 조회
        CategoryEntity category = categoryMapper.selectCategoryById(categoryId);

        // 예외 처리 (데이터가 없으면 정지)
        if (category == null) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
        }

        // DTO 변환
        CategoryResponseDto dto = CategoryResponseDto.from(category);

        // 자식 카테고리가 있는 경우 조회 및 설정
        if (category.isHasChildren()) {
            List<CategoryEntity> children = categoryMapper.selectCategoryChildren(categoryId);
            List<CategoryResponseDto> childrenDto = children.stream()
                    .map(CategoryResponseDto::from)
                    .collect(Collectors.toList());
            dto.setChildren(childrenDto);
        }

        // 상세 조회 시 구독 여부 체크
        if (userId != null) {
            boolean isSubscribed = subscriptionMapper.existsSubscription(userId, categoryId);
            dto.setIsSubscribed(isSubscribed);

            // 자식들도 구독 여부 체크
            if (dto.getChildren() != null) {
                // 내 구독 목록
                List<Integer> subscribedIds = subscriptionMapper.selectSubscribedCategoryIds(userId);
                Set<Integer> subscribedSet = new HashSet<>(subscribedIds);

                for (CategoryResponseDto child : dto.getChildren()) {
                    if (subscribedSet.contains(child.getCategoryId())) {
                        child.setIsSubscribed(true);
                    }
                }
            }
        }

        return dto;
    }
}