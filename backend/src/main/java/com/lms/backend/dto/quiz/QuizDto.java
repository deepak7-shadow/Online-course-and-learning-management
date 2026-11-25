package com.lms.backend.dto.quiz;

import com.lms.backend.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private UUID id;
    private UUID lessonId;
    private String title;
    private String description;
    private Integer passingPercentage;
    private List<QuestionDto> questions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionDto {
        private UUID id;
        private String questionText;
        private QuestionType questionType;
        private Integer points;
        private List<OptionDto> options;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionDto {
        private UUID id;
        private String optionText;
    }
}
