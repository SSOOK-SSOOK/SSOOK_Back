package com.ssook.mvc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity { // 상속
    
    private Long userId;        // DB: user_id (PK)
    private String email;       // DB: email
    private String password;    // DB: password
    private String nickname;    // DB: nickname
    private String role;        // DB: role
    private String intro;       // DB: intro
    
    // createdAt, updatedAt은 부모(BaseEntity)에 있으므로 생략
    
    
	// 회원 정보 수정 편의 메서드
    public void modify(String nickname, String intro) {
        this.nickname = nickname;
        this.intro = intro;
    }
}