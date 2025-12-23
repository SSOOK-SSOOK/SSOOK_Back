package com.ssook.mvc.dto.video.request;

import lombok.Data;

@Data
public class VideoListRequestDto {
    private String categoryName; // 카테고리 명
    private Integer categoryId; // 카테고리 ID
    private Integer sortedType; // 정렬기준(1: 최신순, 2: 조회수순)
    private int page = 1; // 페이지 번호
    private int size = 12; // 페이지당 개수

    // SQL LIMIT에서 사용할 offset
    public int getOffset() {
        return (page - 1) * size;
    }

    private Long userId; // 내 좋아요 여부 확인용
    private Long likedUserId; // 해당 유저가 좋아요한 영상만 가져오기 위함

    private String keyword; // 검색어 (영상 제목)

    private Boolean onlySubscribed; // 구독한 카테고리 영상만 보기
}
