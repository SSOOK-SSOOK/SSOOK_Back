package com.ssook.mvc.repository;

import com.ssook.mvc.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 회원가입 (DB에 저장 성공하면 1, 실패하면 0 반환)
    int insertUser(UserEntity userEntity);
    int existsByEmail(String email);
    int existsByNickname(String nickname);
}