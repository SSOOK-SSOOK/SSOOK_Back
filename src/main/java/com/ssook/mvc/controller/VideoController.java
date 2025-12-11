package com.ssook.mvc.controller;

import com.ssook.mvc.common.ApiResponse;
import com.ssook.mvc.dto.video.request.VideoListRequestDto;
import com.ssook.mvc.dto.video.response.VideoDetailResponseDto;
import com.ssook.mvc.dto.video.response.VideoListResponseDto;
import com.ssook.mvc.service.VideoService;
import com.ssook.mvc.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoController {
    private final VideoService videoService;
    private final JwtUtil jwtUtil;

    @Autowired
    public VideoController(VideoService videoService, JwtUtil jwtUtil) {
        this.videoService = videoService;
        this.jwtUtil = jwtUtil;
    }

    // 영상 목록 조회
    @GetMapping
    public ApiResponse<Map<String, Object>> getVideoList(@ModelAttribute VideoListRequestDto videoListRequestDto) {
        // 서비스 호출로 영상 목록 가져와서 videos에 할당
        List<VideoListResponseDto> videos = videoService.getVideoList(videoListRequestDto);
        // Map에 videos넣기
        Map<String, Object> data = new HashMap<>();
        data.put("videos", videos);

        return ApiResponse.success(data);
    }

    // 영상 상세 조회
    @GetMapping("/{videoId}")
    public ApiResponse<VideoDetailResponseDto> getVideoDetail(@PathVariable Long videoId, @RequestHeader("token") String token) {
        // 토큰에서 유저ID 꺼내기
        Long userId = jwtUtil.getUserId(token);

        // 영상 상세 정보 가져오기
        VideoDetailResponseDto videoDetail = videoService.getVideoDetail(videoId, userId);

        return ApiResponse.success(videoDetail);
    }
}
