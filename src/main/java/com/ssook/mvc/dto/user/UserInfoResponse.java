package com.ssook.mvc.dto.user;

import com.ssook.mvc.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoResponse {
    
    private String email;
    private String nickname;
    private String intro;
    private String role;

    // Entity -> DTO 변환 메서드 (편의상 여기에 만듦)
    public static UserInfoResponse from(UserEntity user) {
        return UserInfoResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .intro(user.getIntro())
                .role(user.getRole())
                .build();
    }
}