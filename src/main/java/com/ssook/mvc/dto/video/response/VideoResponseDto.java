package com.ssook.mvc.dto.video.response;

import lombok.Data;

@Data
public class VideoResponseDto {
    private Long videoId;
    private String thumbnailUrl;
    private Integer viewCount;
}
