package com.lms.backend.dto.lesson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonProgressRequest {
    private Boolean isCompleted;
    private Integer lastWatchedSecond;
}
