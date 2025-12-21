package com.ssook.mvc.dto.user;

import com.ssook.mvc.entity.UserEntity;
import com.ssook.mvc.enums.UserProfile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserJoinRequest {
    
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    private String intro; 

    public UserEntity toEntity(String encodedPassword) {
        return UserEntity.builder()
                .email(this.email)
                .password(encodedPassword)
                .nickname(this.nickname)
                .role("ROLE_USER")
                .intro(this.intro)
                .profileImage(UserProfile.DEFAULT.getUrl()) // 기본 이미지 설정
                .build();
    }
}