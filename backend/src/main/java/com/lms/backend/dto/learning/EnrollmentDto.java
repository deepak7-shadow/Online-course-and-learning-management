package com.lms.backend.dto.learning;

import com.lms.backend.dto.course.CourseSummaryDto;
import com.lms.backend.enums.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto {
    private UUID id;
    private CourseSummaryDto course;
    private BigDecimal progressPercentage;
    private EnrollmentStatus status;
    private Instant enrolledAt;
    private Instant completedAt;
    private String certificateCode;
}
