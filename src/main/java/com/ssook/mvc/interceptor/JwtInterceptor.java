package com.ssook.mvc.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssook.mvc.util.JwtUtil;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 1. 요청의 Method가 'OPTIONS'인 경우 통과 (CORS 예비 요청 처리)
        // Vue.js가 "나 요청 보내도 돼?" 하고 찔러보는 건데 막으면 안 됨
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 2. 헤더에서 'token' 꺼내기
        String token = request.getHeader("token");

        // 3. 토큰이 있고 && 유효한지 검사
        if (token != null && jwtUtil.validateToken(token)) {
            // [꿀팁 적용] 토큰이 유효하면 userId를 꺼내서 요청 객체(request)에 담아둠
            // 이제 Controller에서는 request.getAttribute("userId")로 바로 꺼내 쓸 수 있음!
            Long userId = jwtUtil.getUserId(token);
            request.setAttribute("userId", userId);

            return true; // 통과! (Controller로 이동)
        }

        // [추가] 토큰이 없거나 유효하지 않더라도, "조회(GET)" 기능 중 일부는 허용 (비로그인/게스트 모드)
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // /category/** 또는 /video/** 의 GET 요청은 비로그인 허용
        if ("GET".equalsIgnoreCase(method) &&
                (requestURI.startsWith("/category") || requestURI.startsWith("/video"))) {
            return true; // userId 없이 통과
        }

        // 4. 토큰이 없거나 가짜면 에러 응답 (401 Unauthorized)
        throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
    }
}