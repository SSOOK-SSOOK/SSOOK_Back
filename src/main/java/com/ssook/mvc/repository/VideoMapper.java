package com.ssook.mvc.repository;

import com.ssook.mvc.dto.video.request.VideoListRequestDto;
import com.ssook.mvc.dto.video.response.VideoListResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoMapper {
    List<VideoListResponseDto> selectVideoList(VideoListRequestDto videoListRequestDto);
}
