package com.ssook.mvc.repository;

import com.ssook.mvc.entity.SubscriptionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubscriptionMapper {
    // 구독 추가
    void insertSubscription(SubscriptionEntity subscriptionEntity);

    // 구독 취소
    void deleteSubscription(@Param("userId") Long userId, @Param("categoryId") Integer categoryId);

    // 이미 구독했는지 확인 (중복 방지용)
    boolean existsSubscription(@Param("userId") Long userId, @Param("categoryId") Integer categoryId);

    // 내가 구독한 카테고리 ID 목록 조회 (CategoryService에서 사용 예정)
    List<Integer> selectSubscribedCategoryIds(@Param("userId") Long userId);
}