package com.ssook.mvc.dto.comment.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentResponseDto {
    private Long commentId;
    private Long parentId; // 대댓글일 경우에만 존재
    private Long userId; // 본인확인용 userId(조회기능 만들면서 추가함)
    private String content; // 댓글 내용
    private String nickname; // 댓글 단 유저의 닉네임
    private LocalDateTime createdAt; // 댓글 달린 시간
    private LocalDateTime updatedAt; // 수정된 시간

    private boolean isMyComment; // 내 댓글인지 여부
    private boolean isDeleted; // 삭제된 댓글인지 여부

    private List<CommentResponseDto> replies = new ArrayList<>(); // 대댓글 리스트
}