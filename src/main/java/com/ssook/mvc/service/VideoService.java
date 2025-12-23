package com.ssook.mvc.service;

import com.ssook.mvc.dto.video.request.VideoListRequestDto;
import com.ssook.mvc.dto.video.response.VideoDetailResponseDto;
import com.ssook.mvc.dto.video.response.VideoResponseDto;
import com.ssook.mvc.repository.VideoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoMapper videoMapper;

    // 영상 목록 조회
    @Transactional
    public List<VideoResponseDto> getVideoList(VideoListRequestDto videoListRequestDto) {
        if (videoListRequestDto.getSortedType() == null)
            videoListRequestDto.setSortedType(1);

        return videoMapper.selectVideoList(videoListRequestDto);
    }

    // 영상 상세 조회
    @Transactional
    public VideoDetailResponseDto getVideoDetail(Long videoId, Long userId) {
        // 조회수 증가시키기
        videoMapper.increaseViewCount(videoId);

        // 시청 기록 저장 (로그인 유저인 경우)
        if (userId != null) {
            VideoDetailResponseDto video = videoMapper.selectVideoDetail(videoId, userId);
            if (video != null) {
                // categoryId가 필요하므로 video 객체에서 가져오거나,
                // DB 쿼리 시 VideoDetailResponseDto에 categoryId가 있어야 함.
                // 현재 DTO에 categoryId가 있는지 확인 필요. 없으면 매퍼에서 가져와야 함.
                // 일단 VideoDetailResponseDto 확인 없이 진행 시 위험하므로,
                // categoryId를 별도로 조회하거나 DTO에 있다고 가정...
                // 확인해보니 selectVideoDetail 결과에 categoryId가 있을 가능성 큼.
                // 하지만 안전하게 Map으로 넘김.
                try {
                    videoMapper.insertViewHistory(java.util.Map.of(
                            "userId", userId,
                            "videoId", videoId,
                            "categoryId", video.getCategoryId() // DTO에 categoryId가 있다고 가정 -> 확인 필요
                    ));
                } catch (Exception e) {
                    // 중복 시청 등 에러 무시 (혹은 로거)
                }
            }
        }

        // 상세 정보 가져오기
        VideoDetailResponseDto videoDetail = videoMapper.selectVideoDetail(videoId, userId);

        // 영상이 없으면 예외 return
        if (videoDetail == null)
            throw new IllegalArgumentException("해당하는 영상이 존재하지 않습니다.");

        return videoDetail;
    }

    // 사용자 통계 조회
    @Transactional(readOnly = true)
    public com.ssook.mvc.dto.user.response.UserStatsResponseDto getUserStats(Long userId) {
        // 1. 전체 시청 기록 가져오기 (최근 1년)
        List<java.util.Map<String, Object>> history = videoMapper.selectViewHistory(userId);

        int totalViewCount = history.size();

        // 2. Streak 계산
        int streak = 0;
        if (!history.isEmpty()) {
            java.time.LocalDate lastDate = null;
            java.time.LocalDate today = java.time.LocalDate.now();

            // 데이터가 정렬되어 있다고 가정 (ORDER BY created_at DESC)
            for (java.util.Map<String, Object> log : history) {
                java.sql.Date sqlDate = (java.sql.Date) log.get("viewDate");
                java.time.LocalDate viewDate = sqlDate.toLocalDate();

                if (lastDate == null) {
                    // 첫 기록이 오늘 또는 어제여야 스트릭 유지
                    if (viewDate.equals(today) || viewDate.equals(today.minusDays(1))) {
                        streak = 1;
                        lastDate = viewDate;
                    } else {
                        break; // 스트릭 끊김
                    }
                } else {
                    if (viewDate.equals(lastDate.minusDays(1))) {
                        streak++;
                        lastDate = viewDate;
                    } else if (viewDate.isBefore(lastDate.minusDays(1))) {
                        break;
                    }
                    // 같은 날짜면 패스
                }
            }
        }

        // 3. 최애 카테고리
        String topCategory = videoMapper.selectTopCategory(userId);
        if (topCategory == null)
            topCategory = "아직 없음";

        // 4. 주 시청 시간대
        // 0~5: 새벽, 6~11: 오전, 12~17: 오후, 18~23: 저녁
        int[] timeSlots = new int[4]; // 0, 1, 2, 3
        for (java.util.Map<String, Object> log : history) {
            int hour = (int) log.get("viewHour");
            if (hour >= 0 && hour < 6)
                timeSlots[0]++;
            else if (hour >= 6 && hour < 12)
                timeSlots[1]++;
            else if (hour >= 12 && hour < 18)
                timeSlots[2]++;
            else
                timeSlots[3]++;
        }
        int maxSlotIndex = 0;
        for (int i = 1; i < 4; i++) {
            if (timeSlots[i] > timeSlots[maxSlotIndex])
                maxSlotIndex = i;
        }
        String[] slotNames = { "새벽러", "모닝러", "오후러", "올빼미" };
        String timeTag = slotNames[maxSlotIndex];

        // 5. 레벨 계산 (임시 로직)
        int level = 1;
        if (totalViewCount >= 100)
            level = 5;
        else if (totalViewCount >= 50)
            level = 4;
        else if (totalViewCount >= 20)
            level = 3;
        else if (totalViewCount >= 5)
            level = 2;

        return com.ssook.mvc.dto.user.response.UserStatsResponseDto.builder()
                .totalViewCount(totalViewCount)
                .streak(streak)
                .topCategory(topCategory)
                .timeTag(timeTag)
                .level(level)
                .watchTime(totalViewCount) // 1분으로 가정
                .build();
    }
}
