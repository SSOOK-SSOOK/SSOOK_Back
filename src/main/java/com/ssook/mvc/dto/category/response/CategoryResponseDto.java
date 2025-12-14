package com.ssook.mvc.dto.category.response;

import com.ssook.mvc.entity.CategoryEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class CategoryResponseDto {
    private Integer categoryId;
    private String name;
    private String imageUrl;
    private String description;
    
    @Setter
    private Boolean isSubscribed; 

    public static CategoryResponseDto from(CategoryEntity entity) {
        return CategoryResponseDto.builder()
                .categoryId(entity.getCategoryId())
                .name(entity.getCategoryName())
                .imageUrl(entity.getImageUrl()) 
                .description(entity.getDescription())
                .isSubscribed(false) // 기본값 false (구독 구현 전)
                .build();
    }
}