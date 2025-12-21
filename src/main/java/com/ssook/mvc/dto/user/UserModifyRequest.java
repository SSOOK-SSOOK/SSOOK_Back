package com.ssook.mvc.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserModifyRequest {
    
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;
    
    private String intro; // 한줄 소개는 비어있어도 됨 (NotBlank 안 붙임)

    // 변경할 이미지 경로 (예: "/images/profile/profile3.png")
    private String profileImage;
}