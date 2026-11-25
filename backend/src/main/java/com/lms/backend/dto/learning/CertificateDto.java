package com.lms.backend.dto.learning;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDto {
    private UUID id;
    private String certificateCode;
    private String studentName;
    private String courseTitle;
    private String instructorName;
    private Instant issuedAt;
    private String pdfUrl;
}
