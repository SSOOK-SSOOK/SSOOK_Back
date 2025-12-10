package com.ssook.mvc.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssook.mvc.dto.user.UserJoinRequest;
import com.ssook.mvc.entity.UserEntity;
import com.ssook.mvc.repository.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    @Transactional
    public void signup(UserJoinRequest request) {
        // 1. 이메일 중복 체크
        if (userMapper.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 2. 닉네임 중복 체크
        if (userMapper.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 3. 비밀번호 암호화 (JBCrypt 사용)
        // hashpw: 비밀번호를 암호화해주는 함수
        // gensalt: 암호화할 때 쓰는 '소금(Salt)'을 뿌려서 더 안전하게 만듦
        String encodedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        // 4. DTO -> Entity 변환 (암호화된 비번 넣기)
        UserEntity user = request.toEntity(encodedPassword);

        // 5. DB 저장
        userMapper.saveUser(user);
    }
}