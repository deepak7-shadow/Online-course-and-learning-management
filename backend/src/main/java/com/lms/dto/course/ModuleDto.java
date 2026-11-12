package com.lms.dto.course;

import com.lms.entity.Module;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class ModuleDto {

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "Module title is required")
        private String title;
        private String description;
        private Integer orderIndex;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Response {
        private UUID id;
        private UUID courseId;
        private String title;
        private String description;
        private Integer orderIndex;
        private List<LessonDto.Summary> lessons;
        private OffsetDateTime createdAt;

        public static Response fromEntity(Module m) {
            if (m == null) return null;
            return Response.builder()
                    .id(m.getId())
                    .courseId(m.getCourse() != null ? m.getCourse().getId() : null)
                    .title(m.getTitle())
                    .description(m.getDescription())
                    .orderIndex(m.getOrderIndex())
                    .createdAt(m.getCreatedAt())
                    .build();
        }
    }
}

