package com.ssook.mvc.dto.ai.response;

import lombok.Data;

import java.util.List;

@Data
public class QuizResponseDto {
    private String question; // 문제 내용
    private List<String> options; // 보기 (4개)
    private int answer; // 정답 번호
    private String answerText; // 정답 텍스트 (검증용)
    private String explanation; // 해설 (선택 사항)
}
