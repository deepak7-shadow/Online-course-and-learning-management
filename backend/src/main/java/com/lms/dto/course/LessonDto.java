package com.lms.dto.course;

import com.lms.entity.Lesson;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

public class LessonDto {

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "Lesson title is required")
        private String title;
        private String content;
        private String videoUrl;
        private Integer durationMinutes;
        private Boolean isFreePreview;
        private Integer orderIndex;
        private String type;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Response {
        private UUID id;
        private UUID moduleId;
        private String title;
        private String content;
        private String videoUrl;
        private Integer durationMinutes;
        private Boolean isFreePreview;
        private Integer orderIndex;
        private String type;
        private OffsetDateTime createdAt;

        public static Response fromEntity(Lesson l) {
            if (l == null) return null;
            return Response.builder()
                    .id(l.getId())
                    .moduleId(l.getModule() != null ? l.getModule().getId() : null)
                    .title(l.getTitle())
                    .content(l.getContent())
                    .videoUrl(l.getVideoUrl())
                    .durationMinutes(l.getDurationMinutes())
                    .isFreePreview(l.getIsFreePreview())
                    .orderIndex(l.getOrderIndex())
                    .type(l.getType())
                    .createdAt(l.getCreatedAt())
                    .build();
        }
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Summary {
        private UUID id;
        private String title;
        private Integer durationMinutes;
        private Boolean isFreePreview;
        private Integer orderIndex;
        private String type;

        public static Summary fromEntity(Lesson l) {
            if (l == null) return null;
            return Summary.builder()
                    .id(l.getId())
                    .title(l.getTitle())
                    .durationMinutes(l.getDurationMinutes())
                    .isFreePreview(l.getIsFreePreview())
                    .orderIndex(l.getOrderIndex())
                    .type(l.getType())
                    .build();
        }
    }
}

