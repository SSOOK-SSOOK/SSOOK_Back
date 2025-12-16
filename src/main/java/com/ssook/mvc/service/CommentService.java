package com.ssook.mvc.service;

import com.ssook.mvc.dto.comment.request.CommentRequestDto;
import com.ssook.mvc.dto.comment.response.CommentResponseDto;
import com.ssook.mvc.dto.common.PageResponse;
import com.ssook.mvc.entity.CommentEntity;
import com.ssook.mvc.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    // 댓글 목록 조회
    @Transactional
    public PageResponse<CommentResponseDto> getCommentList(Long videoId, Long userId, int page, int size) {
        // 페이징 계산
        int offset = (page - 1) * size;

        // 부모 댓글 목록 조회
        List<CommentResponseDto> parents = commentMapper.selectParentComments(videoId, size, offset);

        // 전체 개수 조회
        int totalCount = commentMapper.countParentComments(videoId);

        // 부모 댓글이 없을 경우 빈 리스트 반환
        if(parents.isEmpty())
            return new PageResponse<>(Collections.emptyList(), page, size, totalCount);

        // 부모 댓글의 Id 추출해서 List로 변환
        List<Long> parentIds = parents.stream()
                .map(CommentResponseDto::getCommentId)
                .toList();

        // 대댓글 목록 조회
        List<CommentResponseDto> replies = commentMapper.selectRepliesByParentIds(parentIds);

        // 대댓글 그룹화(Map<parentId, List<reply>>)
        Map<Long, List<CommentResponseDto>> replyMap = replies.stream()
                .collect(Collectors.groupingBy(CommentResponseDto::getParentId));

        // 부모 댓글에 대댓글 매핑 및 내 댓글인지 확인
        for(CommentResponseDto parent : parents) {
            List<CommentResponseDto> children = replyMap.getOrDefault(parent.getCommentId(), Collections.emptyList());
            parent.setReplies(children);
            checkIsMyComment(parent, userId);

            for(CommentResponseDto child : children)
                checkIsMyComment(child, userId);
        }

        // 7. 공통 PageResponse 객체로 감싸서 반환
        return new PageResponse<>(parents, page, size, totalCount);
    }




    // 현재 내 Id와 댓글의 Id가 일치하는지 체크하는 보조 메서드
    private void checkIsMyComment(CommentResponseDto commentResponseDto, Long userId) {
        commentResponseDto.setMyComment(commentResponseDto.getUserId().equals(userId));
    }
}
