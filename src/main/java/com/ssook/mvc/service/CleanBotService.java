package com.ssook.mvc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssook.mvc.dto.ai.request.CleanBotRequest;
import com.ssook.mvc.dto.ai.response.CleanBotResponse;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CleanBotService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    // 1. OkHttp 클라이언트 (Cloudflare 우회용)
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    // 2. Jackson ObjectMapper (Spring에서 자동 주입)
    private final ObjectMapper objectMapper;

    public boolean isSafeComment(String content) {
        String url = "https://gms.ssafy.io/gmsapi/api.openai.com/v1/chat/completions";

        try {
            // [Step 1] DTO 객체 생성 (편의 생성자 사용)
            CleanBotRequest requestDto = new CleanBotRequest(model,
                    "다음 댓글이 욕설, 비하, 혐오 표현을 포함하고 있는지 분석해줘. 유해하면 'BAD', 안전하면 'GOOD'이라고만 답해.\n\n댓글 내용: " + content);

            // [Step 2] DTO -> JSON 문자열 변환 (직렬화)
            String jsonBody = objectMapper.writeValueAsString(requestDto);

            // [Step 3] OkHttp 요청 객체 생성
            RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Content-Type", "application/json")
                    .build();

            // [Step 4] 요청 전송 및 응답 처리
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    // JSON -> DTO 변환 (역직렬화)
                    CleanBotResponse responseDto = objectMapper.readValue(response.body().string(), CleanBotResponse.class);

                    // 결과 추출
                    if (responseDto != null && responseDto.getChoices() != null && !responseDto.getChoices().isEmpty()) {
                        String result = responseDto.getChoices().get(0).getMessage().getContent();
                        return !"GOOD".equalsIgnoreCase(result != null ? result.trim() : "");
                    }
                } else {
                    System.err.println("AI 요청 실패: " + response.code());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("AI 요청 중 예외 발생: " + e.getMessage());
        }

        return false; // 에러 발생 시 일단 통과
    }
}