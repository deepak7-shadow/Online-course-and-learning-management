package com.lms.dto.learning;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CertificateDto {
    private UUID id;
    private UUID enrollmentId;
    private UUID studentId;
    private UUID courseId;
    private String courseTitle;
    private String studentName;
    private String certificateCode;
    private OffsetDateTime issuedAt;
    private String pdfUrl;
}

