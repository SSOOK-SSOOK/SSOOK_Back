package com.ssook.mvc.dto.comment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentListResponseDto {
    private List<CommentResponseDto> comments; // 댓글 목록
    private int totalCount; // 전체 댓글 수(대댓글 제외)
    private int totalPage; // 전체 페이지 수
}
