package com.ssook.mvc.controller;

import com.ssook.mvc.common.ApiResponse;
import com.ssook.mvc.dto.comment.request.CommentRequestDto;
import com.ssook.mvc.dto.comment.response.CommentResponseDto;
import com.ssook.mvc.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/video/{video_id}/comment")
    public ApiResponse<CommentResponseDto> createComment(
            @PathVariable("video_id") Long videoId,
            @RequestBody CommentRequestDto commentRequestDto,
            HttpServletRequest request
            ) {
        // request에서 userId 꺼내기
        Long userId = (Long) request.getAttribute("userId");

        // 댓글 저장 후 반환받기
        CommentResponseDto comment = commentService.addComment(videoId, userId, commentRequestDto);

        return new ApiResponse<>(201, "댓글 등록 성공", comment);
    }

    // 대댓글 작성
    @PostMapping("/comment/{comment_id}/reply")
    public ApiResponse<CommentResponseDto> createReply(
            @PathVariable("comment_id") Long commentId,
            @RequestBody CommentRequestDto commentRequestDto,
            HttpServletRequest request
            ) {
        // request에서 userId 꺼내기
        Long userId = (Long) request.getAttribute("userId");

        // 대댓글 저장 후 반환받기
        CommentResponseDto reply = commentService.addReply(commentId, userId, commentRequestDto);

        return new ApiResponse<>(201, "대댓글 등록 성공", reply);
    }
}
