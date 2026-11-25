package com.lms.backend.service;

import com.lms.backend.dto.admin.CourseApprovalRequest;
import com.lms.backend.dto.admin.DashboardMetricsDto;
import com.lms.backend.dto.course.CourseSummaryDto;
import com.lms.backend.dto.user.UserProfileDto;
import com.lms.backend.enums.AccountStatus;
import com.lms.backend.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AdminService {
    DashboardMetricsDto getDashboardMetrics();
    Page<UserProfileDto> getUsers(Role roleFilter, Pageable pageable);
    UserProfileDto updateUserStatus(UUID userId, AccountStatus status);
    UserProfileDto updateUserRole(UUID userId, Role role);
    Page<CourseSummaryDto> getPendingCourses(Pageable pageable);
    void approveOrRejectCourse(UUID courseId, UUID adminId, CourseApprovalRequest request);
}
