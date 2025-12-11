package com.ssook.mvc.repository;

import org.apache.ibatis.annotations.Mapper;

import com.ssook.mvc.entity.UserEntity;

@Mapper
public interface UserMapper {
    // 회원 정보 저장 (Insert)
    void saveUser(UserEntity user);

    // 이메일 중복 체크 (Count가 1 이상이면 true)
    boolean existsByEmail(String email);

    // 닉네임 중복 체크
    boolean existsByNickname(String nickname);
    
    UserEntity findByEmail(String email);
    
    UserEntity findById(Long userId);
    
    void updateUser(UserEntity user);
}