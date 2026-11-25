package com.lms.backend.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardMetricsDto {
    private long totalStudents;
    private long totalInstructors;
    private long totalCourses;
    private long pendingCourseApprovals;
    private long totalEnrollments;
    private long completedEnrollments;
}
