package com.lms.dto.learning;

import com.lms.entity.Enrollment;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class EnrollmentDto {

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Response {
        private UUID id;
        private UUID studentId;
        private UUID courseId;
        private String courseTitle;
        private String courseThumbnail;
        private String status;
        private BigDecimal progressPercentage;
        private OffsetDateTime enrolledAt;
        private OffsetDateTime completedAt;

        public static Response fromEntity(Enrollment e) {
            if (e == null) return null;
            return Response.builder()
                    .id(e.getId())
                    .studentId(e.getStudent() != null ? e.getStudent().getId() : null)
                    .courseId(e.getCourse() != null ? e.getCourse().getId() : null)
                    .courseTitle(e.getCourse() != null ? e.getCourse().getTitle() : null)
                    .courseThumbnail(e.getCourse() != null ? e.getCourse().getThumbnailUrl() : null)
                    .status(e.getStatus())
                    .progressPercentage(e.getProgressPercentage())
                    .enrolledAt(e.getEnrolledAt())
                    .completedAt(e.getCompletedAt())
                    .build();
        }
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class ProgressResponse {
        private UUID enrollmentId;
        private UUID courseId;
        private long totalLessons;
        private long completedLessons;
        private BigDecimal progressPercentage;
        private boolean isCompleted;
    }
}

