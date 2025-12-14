package com.ssook.mvc.repository;

import com.ssook.mvc.dto.comment.response.CommentResponseDto;
import com.ssook.mvc.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    // 댓글 저장
    void saveComment(CommentEntity commentEntity);

    // 저장된 댓글 조회
    CommentResponseDto findCommentById(Long commentId);

    // 부모 댓글의 videoId 조회
    Long findVideoIdByCommentId(Long commentId);
}
