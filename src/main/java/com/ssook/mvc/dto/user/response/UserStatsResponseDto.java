package com.ssook.mvc.dto.user.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStatsResponseDto {
    private int totalViewCount; // 총 시청 개수
    private int watchTime; // 총 시청 시간 (개수 * 1분)
    private int streak; // 연속 학습일
    private int level; // 레벨
    private String topCategory; // 최애 카테고리
    private String timeTag; // 주 시청 시간대 (새벽러, 모닝러 등)
}
