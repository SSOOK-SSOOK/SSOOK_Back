package com.ssook.mvc.dto.video.response;

import lombok.Data;

@Data
public class VideoDetailResponseDto {
    private Long videoId;
    private String videoUrl;
    private String thumbnailUrl;
    private String title;

    private Boolean liked; // 내가 좋아요 했는지
    private Boolean subscribed; // 내가 이 카테고리를 구독했는지
    private Integer likeCount; // 좋아요 수
    private Integer commentCount; // 댓글 수
    private Integer viewCount; // 조회수
    private Integer categoryId; // 카테고리 ID (통계용)
}
