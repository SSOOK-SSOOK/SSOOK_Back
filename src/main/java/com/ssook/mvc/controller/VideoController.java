package com.ssook.mvc.controller;

import com.ssook.mvc.common.ApiResponse;
import com.ssook.mvc.dto.video.request.VideoListRequestDto;
import com.ssook.mvc.dto.video.response.VideoDetailResponseDto;
import com.ssook.mvc.dto.video.response.VideoResponseDto;
import com.ssook.mvc.service.VideoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    // 영상 목록 조회
    @GetMapping
    public ApiResponse<Map<String, Object>> getVideoList(@ModelAttribute VideoListRequestDto videoListRequestDto,
            HttpServletRequest request) {
        // request에서 userId 꺼내기 (로그인 안했으면 null일 수 있음 -> 필터 확인 필요하나 보통 getAttribute는 null
        // 가능)
        Long userId = (Long) request.getAttribute("userId");
        videoListRequestDto.setUserId(userId);

        // 서비스 호출로 영상 목록 가져와서 videos에 할당
        List<VideoResponseDto> videos = videoService.getVideoList(videoListRequestDto);
        // Map에 videos넣기
        Map<String, Object> data = new HashMap<>();
        data.put("videos", videos);

        return ApiResponse.success(data);
    }

    // 영상 상세 조회
    @GetMapping("/{videoId}")
    public ApiResponse<VideoDetailResponseDto> getVideoDetail(@PathVariable Long videoId, HttpServletRequest request) {
        // request에서 유저ID 꺼내기
        Long userId = (Long) request.getAttribute("userId");

        // 영상 상세 정보 가져오기
        VideoDetailResponseDto videoDetail = videoService.getVideoDetail(videoId, userId);

        return ApiResponse.success(videoDetail);
    }

    // 내가 좋아요한 영상 모아보기
    @GetMapping("/my-likes")
    public ApiResponse<Map<String, Object>> getMyLikedVideos(
            @ModelAttribute VideoListRequestDto requestDto,
            HttpServletRequest request
    ) {
        // 1. Interceptor에서 검증 후 넣어준 userId 꺼내기
        Long userId = (Long) request.getAttribute("userId");

        // 2. DTO 설정
        requestDto.setUserId(userId);       // 영상마다 내가 좋아요 눌렀는지 확인용
        requestDto.setLikedUserId(userId);  // 이 유저가 좋아요한 영상만 필터링해서 가져옴

        // 3. Service 호출 (기존 목록 조회 로직 재사용)
        List<VideoResponseDto> likedVideos = videoService.getVideoList(requestDto);

        // 4. 응답 포맷 통일 (기존 getVideoList와 동일하게 "videos" 키에 담아서 리턴)
        Map<String, Object> data = new HashMap<>();
        data.put("videos", likedVideos);

        return ApiResponse.success(data);
    }
}
