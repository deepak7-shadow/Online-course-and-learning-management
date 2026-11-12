package com.lms.dto.course;

import com.lms.entity.Course;
import com.lms.enums.CourseStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class CourseDto {

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class CreateRequest {
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be at most 255 characters")
        private String title;

        private String subtitle;
        private String description;
        private String thumbnailUrl;

        @DecimalMin(value = "0.0", message = "Price must be non-negative")
        private BigDecimal price;

        private UUID categoryId;
        private String level;
        private String language;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class UpdateRequest {
        @Size(max = 255)
        private String title;
        private String subtitle;
        private String description;
        private String thumbnailUrl;
        private BigDecimal price;
        private UUID categoryId;
        private String level;
        private String language;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Response {
        private UUID id;
        private String title;
        private String slug;
        private String subtitle;
        private String description;
        private String thumbnailUrl;
        private BigDecimal price;
        private String categoryName;
        private UUID categoryId;
        private UUID instructorId;
        private String instructorName;
        private CourseStatus status;
        private String level;
        private String language;
        private long totalEnrollments;
        private Double averageRating;
        private long totalReviews;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public static Response fromEntity(Course c) {
            if (c == null) return null;
            return Response.builder()
                    .id(c.getId())
                    .title(c.getTitle())
                    .slug(c.getSlug())
                    .subtitle(c.getSubtitle())
                    .description(c.getDescription())
                    .thumbnailUrl(c.getThumbnailUrl())
                    .price(c.getPrice())
                    .categoryId(c.getCategory() != null ? c.getCategory().getId() : null)
                    .categoryName(c.getCategory() != null ? c.getCategory().getName() : null)
                    .instructorId(c.getInstructor() != null ? c.getInstructor().getId() : null)
                    .instructorName(c.getInstructor() != null ? c.getInstructor().getFullName() : null)
                    .status(c.getStatus())
                    .level(c.getLevel())
                    .language(c.getLanguage())
                    .createdAt(c.getCreatedAt())
                    .updatedAt(c.getUpdatedAt())
                    .build();
        }
    }
}

