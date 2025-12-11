package com.ssook.mvc.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssook.mvc.dto.user.UserInfoResponse;
import com.ssook.mvc.dto.user.UserJoinRequest;
import com.ssook.mvc.dto.user.UserLoginRequest;
import com.ssook.mvc.entity.UserEntity;
import com.ssook.mvc.repository.UserMapper;
import com.ssook.mvc.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final JwtUtil jwtUtil; // JwtUtil 주입 (RequiredArgsConstructor 덕분에 자동 주입됨)
	
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
    
    @Transactional(readOnly = true) // 읽기 전용 모드 (성능 최적화)
    public String login(UserLoginRequest request) {
        // 1. 이메일로 사용자 조회
        UserEntity user = userMapper.findByEmail(request.getEmail());
        
        // 2. 사용자가 없거나, 비밀번호가 틀리면 에러
        if (user == null || !BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        // 3. 인증 성공 시 토큰 생성 후 반환
        return jwtUtil.generateToken(user.getUserId(), user.getEmail());
    }
    
    // 내 정보 조회
    @Transactional(readOnly = true)
    public UserInfoResponse getMyInfo(Long userId) {
        // DB에서 조회
        UserEntity user = userMapper.findById(userId);
        
        // 없으면 에러 (혹시 탈퇴했거나 잘못된 토큰일 경우)
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        
        // Entity -> DTO 변환해서 반환 (비밀번호 제외됨)
        return UserInfoResponse.from(user);
    }
    
}