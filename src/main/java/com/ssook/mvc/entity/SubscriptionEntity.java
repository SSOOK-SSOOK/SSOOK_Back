package com.ssook.mvc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionEntity {
    private Long subscriptionId; // PK
    private Long userId;         // 구독한 유저
    private Integer categoryId;  // 구독된 카테고리 (CategoryEntity가 Integer)
    
}