package com.lms.backend.dto.course;

import com.lms.backend.enums.CourseLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseRequest {
    @NotBlank
    private String title;
    private String subtitle;
    private String description;
    @NotNull
    private UUID categoryId;
    private BigDecimal price;
    @NotNull
    private CourseLevel level;
    private String thumbnailUrl;
    private String promoVideoUrl;
}
