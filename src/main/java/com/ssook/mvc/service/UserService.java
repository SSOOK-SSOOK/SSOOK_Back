package com.ssook.mvc.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssook.mvc.dto.UserDto;
import com.ssook.mvc.mapper.UserMapper;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder; // 암호화 기계 주입

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signup(UserDto userDto) {
        // 1. 중복 검사 (실무 필수!)
        if (userMapper.existsByEmail(userDto.getEmail()) > 0) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (userMapper.existsByNickname(userDto.getNickname()) > 0) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 2. 비밀번호 암호화 (절대 평문 저장 금지!)
        // "1234" -> "$2a$10$rXn..." 처럼 알아볼 수 없는 문자로 바뀜
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        // 3. DB 저장
        userMapper.insertUser(userDto);
    }
}