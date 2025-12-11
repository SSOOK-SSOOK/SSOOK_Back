package com.ssook.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssook.mvc.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") // 1. 기본적으로 모든 URL을 막음
                .excludePathPatterns(   // 2. 아래 URL들은 검사 제외 (화이트리스트)
                        "/users/signup",  // 회원가입
                        "/users/login",   // 로그인
                        "/swagger-ui/**", // 스웨거 (문서)
                        "/v3/api-docs/**" // 스웨거 (문서)
                );
    }
}