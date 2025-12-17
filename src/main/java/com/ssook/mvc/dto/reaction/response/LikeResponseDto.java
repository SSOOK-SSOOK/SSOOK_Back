package com.ssook.mvc.dto.reaction.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeResponseDto {
    private boolean liked; // 좋아요 여부(true / false)
    private int likeCount; // 좋아요 수
}
