package com.ssook.mvc.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key;
    private final long expiration = 1000*60*60*24*14; // 1시간 할건데 일단 테스트때는 2주!

    // 생성자: application.properties에서 jwt.secret 값을 가져와서 세팅
    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 토큰 생성 메서드
    public String generateToken(Long userId, String email) {
        return Jwts.builder()
                .setSubject(email)                  // 토큰 제목 (보통 이메일이나 ID)
                .claim("userId", userId)            // 추가 정보 (PK)
                .setIssuedAt(new Date())            // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 암호화 알고리즘
                .compact();
    }
    
    // 토큰 유효성 검사 (위조? 만료? 등등 체크)
    public boolean validateToken(String token) {
        try {
            // 토큰을 파싱해서 서명이 맞는지, 만료되지 않았는지 확인
            // 문제가 있으면 알아서 에러(Exception)를 던집니다.
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // 서명이 틀렸거나, 만료되었거나, 형식이 이상하면 false 반환
            return false;
        }
    }

    // 토큰에서 UserId 꺼내기 (꿀팁용)
    public Long getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class); // generateToken에서 넣었던 키값("userId")
    }
}