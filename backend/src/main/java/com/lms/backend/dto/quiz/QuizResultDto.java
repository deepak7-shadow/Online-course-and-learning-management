package com.lms.backend.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultDto {
    private BigDecimal score;
    private boolean passed;
    private int totalQuestions;
    private int correctAnswers;
    private String message;
}
