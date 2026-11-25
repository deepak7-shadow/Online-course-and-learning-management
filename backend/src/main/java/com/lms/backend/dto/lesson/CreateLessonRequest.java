package com.lms.backend.dto.lesson;

import com.lms.backend.enums.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLessonRequest {
    @NotBlank
    private String title;
    @NotNull
    private ContentType contentType;
    private String videoUrl;
    private String contentText;
    private Integer durationMinutes;
    private Integer orderIndex;
    private Boolean isFreePreview;
}
