package com.lms.dto.category;

import com.lms.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

public class CategoryDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "Category name is required")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        private String name;

        private String slug;

        private String description;

        @Builder.Default
        private Boolean isActive = true;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private UUID id;
        private String name;
        private String slug;
        private String description;
        private Boolean isActive;
        private OffsetDateTime createdAt;

        public static Response fromEntity(Category entity) {
            if (entity == null) return null;
            return Response.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .slug(entity.getSlug())
                    .description(entity.getDescription())
                    .isActive(entity.getIsActive())
                    .createdAt(entity.getCreatedAt())
                    .build();
        }
    }
}
