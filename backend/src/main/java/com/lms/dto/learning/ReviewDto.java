package com.lms.dto.learning;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ReviewDto {

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Request {
        private Integer rating;
        private String comment;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Response {
        private UUID id;
        private UUID courseId;
        private UUID studentId;
        private String studentName;
        private Integer rating;
        private String comment;
        private OffsetDateTime createdAt;
    }
}

