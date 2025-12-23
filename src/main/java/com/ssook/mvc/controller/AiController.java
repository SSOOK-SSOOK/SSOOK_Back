package com.ssook.mvc.controller;

import com.ssook.mvc.common.ApiResponse;
import com.ssook.mvc.dto.ai.request.QuizRequestDto;
import com.ssook.mvc.dto.ai.response.QuizResponseDto;
import com.ssook.mvc.service.AiService; // 변경됨
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService; // QuizService -> AiService 변경

    @PostMapping("/quiz")
    public ApiResponse<List<QuizResponseDto>> createQuiz(@RequestBody QuizRequestDto request) {
        // 메서드 호출 변경
        List<QuizResponseDto> quizzes = aiService.generateQuiz(request.getKeyword());
        return ApiResponse.success(quizzes);
    }
}