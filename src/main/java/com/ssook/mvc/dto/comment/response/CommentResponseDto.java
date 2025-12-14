package com.ssook.mvc.dto.comment.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {
    private Long commentId;
    private Long parentId; // 대댓글일 경우에만 존재
    private String content; // 댓글 내용
    private String nickname; // 댓글 단 유저의 닉네임
    private LocalDateTime createdAt; // 댓글 달린 시간
}
