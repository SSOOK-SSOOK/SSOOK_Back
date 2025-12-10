package com.ssook.mvc.controller;

import com.ssook.mvc.common.ApiResponse;
import com.ssook.mvc.dto.video.request.VideoListRequestDto;
import com.ssook.mvc.dto.video.response.VideoListResponseDto;
import com.ssook.mvc.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoController {
    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
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
}
