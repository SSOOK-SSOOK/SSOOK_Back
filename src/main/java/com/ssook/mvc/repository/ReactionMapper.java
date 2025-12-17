package com.ssook.mvc.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReactionMapper {
    // 좋아요 존재 여부 확인 (1: 존재, 0: 없음 -> boolean 자동 변환)
    boolean existReaction(@Param("userId") Long userId, @Param("videoId") Long videoId);

    // 좋아요 추가 (INSERT)
    void insertReaction(@Param("userId") Long userId, @Param("videoId") Long videoId);

    // 좋아요 취소 (DELETE)
    void deleteReaction(@Param("userId") Long userId, @Param("videoId") Long videoId);

    // 영상의 총 좋아요 수 조회
    int countReaction(Long videoId);
}
