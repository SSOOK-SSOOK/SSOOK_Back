package com.ssook.mvc.service;

import com.ssook.mvc.dto.reaction.response.LikeResponseDto;
import com.ssook.mvc.repository.ReactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReactionService {
    private final ReactionMapper reactionMapper;

    // 좋아요 있으면 -> 좋아요 삭제
    // 좋아요 없으면 -> 좋아요 추가
    @Transactional
    public LikeResponseDto toggleLike(Long userId, Long videoId) {
        // 좋아요 존재 여부 체크
        boolean isLiked = reactionMapper.existReaction(userId, videoId);

        if(isLiked) {
            // 이미 좋아요 존재 -> 삭제
            reactionMapper.deleteReaction(userId, videoId);
            isLiked = false;
        } else {
            // 좋아요 없음 -> 추가
            reactionMapper.insertReaction(userId, videoId);
            isLiked = true;
        }

        // 좋아요 개수 조회
        int likeCount = reactionMapper.countReaction(videoId);

        // 결과 반환
        return LikeResponseDto.builder()
                .liked(isLiked)
                .likeCount(likeCount)
                .build();
    }
}
