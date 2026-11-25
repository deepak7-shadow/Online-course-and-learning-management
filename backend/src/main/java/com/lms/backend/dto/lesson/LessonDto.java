package com.lms.backend.dto.lesson;

import com.lms.backend.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {
    private UUID id;
    private UUID moduleId;
    private String title;
    private ContentType contentType;
    private String videoUrl;
    private String contentText;
    private Integer durationMinutes;
    private Integer orderIndex;
    private Boolean isFreePreview;
    private Boolean isCompleted;
}
