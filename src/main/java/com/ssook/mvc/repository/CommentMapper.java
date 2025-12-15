package com.ssook.mvc.repository;

import com.ssook.mvc.dto.comment.response.CommentResponseDto;
import com.ssook.mvc.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    // 댓글 저장
    void saveComment(CommentEntity commentEntity);

    // 저장된 댓글 조회
    CommentResponseDto findCommentById(Long commentId);

    // 부모 댓글의 videoId 조회
    Long findVideoIdByCommentId(Long commentId);

    // 부모 댓글 목록 조회
    List<CommentResponseDto> selectParentComments(@Param("videoId") Long videoId,
                                                  @Param("limit") int limit,
                                                  @Param("offset") int offset);

    // 부모 댓글의 대댓글 목록 조회
    List<CommentResponseDto> selectRepliesByParentIds(@Param("parentIds") List<Long> parentId);

    // 전체 부모 댓글 개수
    int countParentComments(Long videoId);
}
