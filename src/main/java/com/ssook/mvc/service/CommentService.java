package com.ssook.mvc.service;

import com.ssook.mvc.dto.comment.request.CommentRequestDto;
import com.ssook.mvc.dto.comment.response.CommentResponseDto;
import com.ssook.mvc.entity.CommentEntity;
import com.ssook.mvc.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    // 댓글 작성
    @Transactional
    public CommentResponseDto addComment(Long videoId, Long userId, CommentRequestDto commentRequestDto) {
        // CommentEntity 생성
        CommentEntity commentEntity = CommentEntity.builder()
                .videoId(videoId)
                .userId(userId)
                .content(commentRequestDto.getContent())
                .build();

        // 댓글 저장
        commentMapper.saveComment(commentEntity);

        // 저장된 댓글 정보 반환
        return commentMapper.findCommentById(commentEntity.getCommentId());
    }

    // 대댓글 작성
    @Transactional
    public CommentResponseDto addReply(Long parentId, Long userId, CommentRequestDto commentRequestDto) {
        // 부모 댓글의 videoId 조회
        Long videoId = commentMapper.findVideoIdByCommentId(parentId);
        // 영상이 없을 경우 예외 발생
        if (videoId == null) {
            throw new IllegalArgumentException("존재하지 않는 부모 댓글입니다.");
        }

        // CommentEntity 생성
        CommentEntity reply = CommentEntity.builder()
                .videoId(videoId)
                .userId(userId)
                .parentId(parentId)
                .content(commentRequestDto.getContent())
                .build();

        // 댓글 저장
        commentMapper.saveComment(reply);

        // 저장된 댓글 정보 반환
        return commentMapper.findCommentById(reply.getCommentId());
    }
}
