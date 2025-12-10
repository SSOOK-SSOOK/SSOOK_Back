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
    private final long expiration = 1000*60*60; // 1시간

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
}