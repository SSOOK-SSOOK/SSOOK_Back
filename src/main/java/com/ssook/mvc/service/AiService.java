package com.ssook.mvc.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssook.mvc.dto.ai.request.CleanBotRequest;
import com.ssook.mvc.dto.ai.response.CleanBotResponse;
import com.ssook.mvc.dto.ai.response.QuizResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String baseUrl;

    private final ObjectMapper objectMapper;

    // Cloudflare 차단 우회를 위한 OkHttp 클라이언트
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    /**
     * 공통 API 호출 메서드
     */
    private String callOpenAiApi(String prompt) {
        try {
            CleanBotRequest requestDto = new CleanBotRequest(model, prompt);
            String jsonBody = objectMapper.writeValueAsString(requestDto);

            RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(baseUrl)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseString = response.body().string();
                    CleanBotResponse responseDto = objectMapper.readValue(responseString, CleanBotResponse.class);

                    if (responseDto != null && !responseDto.getChoices().isEmpty()) {
                        return responseDto.getChoices().get(0).getMessage().getContent();
                    }
                } else {
                    log.error("AI 요청 실패: code={}, body={}", response.code(),
                            response.body() != null ? response.body().string() : "null");
                }
            }
        } catch (IOException e) {
            log.error("AI 서비스 연결 오류", e);
            throw new RuntimeException("AI 서비스 연결 중 오류가 발생했습니다.");
        }
        return null;
    }

    /**
     * 기능 1: 퀴즈 생성
     */
    public List<QuizResponseDto> generateQuiz(String keyword) {
        String prompt = String.format(
                "주제 '%s'에 대한 초보자 수준의 객관식 퀴즈 3개를 만들어줘. " +
                        "정답(answer)은 0~3 사이의 인덱스여야 히고, answerText는 그 인덱스에 해당하는 보기를 그대로 적어. " +
                        "반드시 아래 JSON 포맷을 지켜서 배열로 반환해. 다른 말은 하지 마. " +
                        "JSON 예시: " +
                        "[{\"question\":\"문제내용\", \"options\":[\"보기1\",\"보기2\",\"보기3\",\"보기4\"], \"answer\":2, \"answerText\":\"보기3\", \"explanation\":\"해설\"}]",
                keyword);

        String content = callOpenAiApi(prompt);

        if (content != null) {
            try {
                // 코드 블록 제거 및 JSON 배열 부분만 추출
                int firstIndex = content.indexOf("[");
                int lastIndex = content.lastIndexOf("]");

                if (firstIndex != -1 && lastIndex != -1) {
                    content = content.substring(firstIndex, lastIndex + 1);
                    List<QuizResponseDto> quizzes = objectMapper.readValue(content, new TypeReference<>() {
                    });

                    // 검증 로직: answerText를 기반으로 answer 인덱스 재설정
                    for (QuizResponseDto quiz : quizzes) {
                        if (quiz.getAnswerText() != null && quiz.getOptions() != null) {
                            int correctIdx = -1;
                            for (int i = 0; i < quiz.getOptions().size(); i++) {
                                // 텍스트 비교 (공백 제거 후)
                                if (quiz.getOptions().get(i).trim().equalsIgnoreCase(quiz.getAnswerText().trim())) {
                                    correctIdx = i;
                                    break;
                                }
                            }
                            // 텍스트가 일치하는 보기가 있으면 그 인덱스로 덮어씌움
                            if (correctIdx != -1) {
                                quiz.setAnswer(correctIdx);
                            }
                        }
                    }
                    return quizzes;
                } else {
                    log.warn("AI 응답에서 JSON 배열을 찾을 수 없습니다: {}", content);
                }
            } catch (Exception e) {
                log.error("퀴즈 파싱 실패. content={}", content, e);
            }
        }
        return new ArrayList<>();
    }

    /**
     * 기능 2: 댓글 유해성 검사 (True = 유해함, False = 안전함)
     */
    public boolean isToxicComment(String content) {
        String prompt = "다음 댓글이 욕설, 비하, 혐오 표현을 포함하고 있는지 분석해줘. 유해하면 'BAD', 안전하면 'GOOD'이라고만 답해.\n\n댓글 내용: " + content;

        String result = callOpenAiApi(prompt);

        // 결과가 없거나 "GOOD"이 아니면 유해(Toxic)한 것으로 판단
        return !"GOOD".equalsIgnoreCase(result != null ? result.trim() : "");
    }
}