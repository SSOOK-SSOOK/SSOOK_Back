package com.ssook.mvc.mapper;

import com.ssook.mvc.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 회원가입 (DB에 저장 성공하면 1, 실패하면 0 반환)
    int insertUser(UserDto userDto);
    int existsByEmail(String email);
    int existsByNickname(String nickname);
}