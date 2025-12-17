package com.ssook.mvc.controller;

import com.ssook.mvc.common.ApiResponse;
import com.ssook.mvc.dto.reaction.response.LikeResponseDto;
import com.ssook.mvc.service.ReactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReactionController {
    private final ReactionService reactionService;

    // 좋아요 등록 및 취소
    @PostMapping("/video/{videoId}/like")
    public ApiResponse<LikeResponseDto> toggleLike(
            @PathVariable("videoId") Long videoId,
            HttpServletRequest request
    ) {
        // request에서 userId 꺼내기
        Long userId = (Long) request.getAttribute("userId");

        // 서비스 호츨
        LikeResponseDto likeResponseDto = reactionService.toggleLike(userId, videoId);

        return new ApiResponse<>(200, "성공", likeResponseDto);
    }
}
