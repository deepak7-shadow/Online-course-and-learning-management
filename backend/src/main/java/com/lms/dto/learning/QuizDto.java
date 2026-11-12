package com.lms.dto.learning;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class QuizDto {

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Request {
        private String title;
        private String description;
        private Integer passingScore;
        private Integer timeLimitMinutes;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Response {
        private UUID id;
        private String title;
        private String description;
        private Integer passingScore;
        private Integer timeLimitMinutes;
        private int totalQuestions;
        private OffsetDateTime createdAt;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class SubmitRequest {
        // Map of questionId (String) -> chosen answer(s) (String)
        private java.util.Map<String, String> answers;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class AttemptResult {
        private UUID attemptId;
        private UUID quizId;
        private BigDecimal score;
        private boolean isPassed;
        private int passingScore;
        private OffsetDateTime attemptedAt;
    }
}

