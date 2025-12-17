package com.ssook.mvc.service;

import com.ssook.mvc.entity.SubscriptionEntity;
import com.ssook.mvc.repository.CategoryMapper;
import com.ssook.mvc.repository.SubscriptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionMapper subscriptionMapper;
    private final CategoryMapper categoryMapper; // 카테고리 존재 확인용

    // 구독하기
    @Transactional
    public void subscribe(Long userId, Integer categoryId) {
        // 존재하지 않는 카테고리면 에러
        if (categoryMapper.selectCategoryById(categoryId) == null) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
        }

        // 이미 구독 중이면 에러 (혹은 무시)
        if (subscriptionMapper.existsSubscription(userId, categoryId)) {
            throw new IllegalArgumentException("이미 구독 중인 카테고리입니다.");
        }

        // 저장
        SubscriptionEntity subscription = SubscriptionEntity.builder()
                .userId(userId)
                .categoryId(categoryId)
                .build();
        
        subscriptionMapper.insertSubscription(subscription);
    }

    // 구독 취소하기
    @Transactional
    public void unsubscribe(Long userId, Integer categoryId) {
        // 구독 내역이 없으면 에러
        if (!subscriptionMapper.existsSubscription(userId, categoryId)) {
            throw new IllegalArgumentException("구독 중인 카테고리가 아닙니다.");
        }

        // 삭제
        subscriptionMapper.deleteSubscription(userId, categoryId);
    }
}