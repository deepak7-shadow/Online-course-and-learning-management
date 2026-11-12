package com.lms.service;

import com.lms.dto.admin.*;
import com.lms.dto.common.PageResponse;
import com.lms.dto.user.UserProfileDto;
import com.lms.enums.AccountStatus;
import com.lms.enums.Role;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AdminService {

    PageResponse<UserProfileDto> getAllUsers(String query, Role role, AccountStatus status, Pageable pageable);

    UserProfileDto getUserById(UUID userId);

    UserProfileDto updateUserStatus(UUID userId, UserStatusUpdateRequest request);

    UserProfileDto updateUserRole(UUID userId, UserRoleUpdateRequest request);

    PageResponse<AdminCourseDto> getPendingCourses(Pageable pageable);

    AdminCourseDto approveOrRejectCourse(UUID courseId, UUID adminId, CourseApprovalRequest request);

    AdminStatsDto getAdminStats();
}

