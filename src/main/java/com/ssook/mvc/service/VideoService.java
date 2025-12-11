package com.ssook.mvc.service;

import com.ssook.mvc.dto.video.request.VideoListRequestDto;
import com.ssook.mvc.dto.video.response.VideoDetailResponseDto;
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

    // 영상 상세 조회
    @Transactional
    public VideoDetailResponseDto getVideoDetail(Long videoId, Long userId) {
        // 조회수 증가시키기
        videoMapper.increaseViewCount(videoId);

        // 상세 정보 가져오기
        VideoDetailResponseDto videoDetail = videoMapper.selectVideoDetail(videoId, userId);

        // 영상이 없으면 예외 return
        if(videoDetail == null)
            throw new IllegalArgumentException("해당하는 영상이 존재하지 않습니다.");

        return videoDetail;
    }
}
