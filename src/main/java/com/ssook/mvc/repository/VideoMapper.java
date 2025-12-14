package com.ssook.mvc.repository;

import com.ssook.mvc.dto.video.request.VideoListRequestDto;
import com.ssook.mvc.dto.video.response.VideoDetailResponseDto;
import com.ssook.mvc.dto.video.response.VideoResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoMapper {
    // 영상 목록 조회
    List<VideoResponseDto> selectVideoList(VideoListRequestDto videoListRequestDto);

    // 영상 상세 조회
    VideoDetailResponseDto selectVideoDetail(@Param("videoId") Long videoId, @Param("userId") Long userId);

    // 조회수 증가
    void increaseViewCount(Long videoId);
}
