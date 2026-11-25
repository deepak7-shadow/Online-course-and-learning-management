package com.lms.backend.dto.course;

import com.lms.backend.enums.CourseLevel;
import com.lms.backend.enums.CourseStatus;
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
public class CourseSummaryDto {
    private UUID id;
    private String title;
    private String slug;
    private String subtitle;
    private String categoryName;
    private String instructorName;
    private BigDecimal price;
    private CourseLevel level;
    private CourseStatus status;
    private String thumbnailUrl;
    private int totalLessons;
    private int totalDurationMinutes;
    private Double averageRating;
    private Long enrollmentCount;
}
