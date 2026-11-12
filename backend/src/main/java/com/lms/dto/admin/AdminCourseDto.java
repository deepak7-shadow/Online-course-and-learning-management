package com.lms.dto.admin;

import com.lms.entity.Course;
import com.lms.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminCourseDto {

    private UUID id;
    private String title;
    private String slug;
    private String subtitle;
    private BigDecimal price;
    private CourseStatus status;
    private String categoryName;
    private UUID instructorId;
    private String instructorName;
    private String instructorEmail;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static AdminCourseDto fromEntity(Course course) {
        if (course == null) return null;
        return AdminCourseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .slug(course.getSlug())
                .subtitle(course.getSubtitle())
                .price(course.getPrice())
                .status(course.getStatus())
                .categoryName(course.getCategory() != null ? course.getCategory().getName() : null)
                .instructorId(course.getInstructor() != null ? course.getInstructor().getId() : null)
                .instructorName(course.getInstructor() != null ? course.getInstructor().getFullName() : null)
                .instructorEmail(course.getInstructor() != null ? course.getInstructor().getEmail() : null)
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}

