package com.ssook.mvc.service;

import com.ssook.mvc.dto.video.request.VideoListRequestDto;
import com.ssook.mvc.dto.video.response.VideoListResponseDto;
import com.ssook.mvc.repository.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoService {
    private final VideoMapper videoMapper;

    @Autowired
    public VideoService(VideoMapper videoMapper) {
        this.videoMapper = videoMapper;
    }

    // 영상 목록 조회
    @Transactional
    public List<VideoListResponseDto> getVideoList(VideoListRequestDto videoListRequestDto) {
        if(videoListRequestDto.getSortedType() == null)
            videoListRequestDto.setSortedType(1);

        return videoMapper.selectVideoList(videoListRequestDto);
    }
}
