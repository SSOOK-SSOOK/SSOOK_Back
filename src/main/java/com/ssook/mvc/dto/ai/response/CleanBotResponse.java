package com.ssook.mvc.dto.ai.response;

import com.ssook.mvc.dto.ai.request.CleanBotRequest;
import lombok.Data;

import java.util.List;

@Data
public class CleanBotResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private int index;
        private CleanBotRequest.Message message;
    }
}
