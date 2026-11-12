package com.lms.service.impl;

import com.lms.dto.admin.*;
import com.lms.dto.common.PageResponse;
import com.lms.dto.user.UserProfileDto;
import com.lms.entity.Course;
import com.lms.entity.CourseApproval;
import com.lms.entity.UserProfile;
import com.lms.enums.AccountStatus;
import com.lms.enums.ApprovalDecision;
import com.lms.enums.CourseStatus;
import com.lms.enums.Role;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.CourseApprovalRepository;
import com.lms.repository.CourseRepository;
import com.lms.repository.UserProfileRepository;
import com.lms.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserProfileRepository userProfileRepository;
    private final CourseRepository courseRepository;
    private final CourseApprovalRepository courseApprovalRepository;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserProfileDto> getAllUsers(String query, Role role, AccountStatus status, Pageable pageable) {
        Page<UserProfile> usersPage = userProfileRepository.filterUsers(query, role, status, pageable);
        return PageResponse.from(usersPage.map(UserProfileDto::fromEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto getUserById(UUID userId) {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        return UserProfileDto.fromEntity(user);
    }

    @Override
    @Transactional
    public UserProfileDto updateUserStatus(UUID userId, UserStatusUpdateRequest request) {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        log.info("Admin updated status for user {} from {} to {}. Reason: {}",
                userId, user.getStatus(), request.getStatus(), request.getReason());

        user.setStatus(request.getStatus());
        UserProfile updated = userProfileRepository.save(user);
        return UserProfileDto.fromEntity(updated);
    }

    @Override
    @Transactional
    public UserProfileDto updateUserRole(UUID userId, UserRoleUpdateRequest request) {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        log.info("Admin changed role for user {} from {} to {}", userId, user.getRole(), request.getRole());

        user.setRole(request.getRole());
        UserProfile updated = userProfileRepository.save(user);
        return UserProfileDto.fromEntity(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AdminCourseDto> getPendingCourses(Pageable pageable) {
        Page<Course> pendingCourses = courseRepository.findByStatus(CourseStatus.PENDING_APPROVAL, pageable);
        return PageResponse.from(pendingCourses.map(AdminCourseDto::fromEntity));
    }

    @Override
    @Transactional
    public AdminCourseDto approveOrRejectCourse(UUID courseId, UUID adminId, CourseApprovalRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + courseId));

        UserProfile admin = userProfileRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin user not found with ID: " + adminId));

        CourseStatus newStatus = (request.getDecision() == ApprovalDecision.APPROVED)
                ? CourseStatus.PUBLISHED
                : CourseStatus.REJECTED;

        course.setStatus(newStatus);
        Course savedCourse = courseRepository.save(course);

        CourseApproval approval = CourseApproval.builder()
                .course(savedCourse)
                .admin(admin)
                .decision(request.getDecision())
                .comments(request.getComments())
                .build();
        courseApprovalRepository.save(approval);

        log.info("Course {} decision by admin {}: {}", courseId, adminId, request.getDecision());
        return AdminCourseDto.fromEntity(savedCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminStatsDto getAdminStats() {
        return AdminStatsDto.builder()
                .totalUsers(userProfileRepository.count())
                .totalStudents(userProfileRepository.countByRole(Role.ROLE_STUDENT))
                .totalInstructors(userProfileRepository.countByRole(Role.ROLE_INSTRUCTOR))
                .totalAdmins(userProfileRepository.countByRole(Role.ROLE_ADMIN))
                .activeUsers(userProfileRepository.countByStatus(AccountStatus.ACTIVE))
                .suspendedUsers(userProfileRepository.countByStatus(AccountStatus.SUSPENDED))
                .pendingCourses(courseRepository.countByStatus(CourseStatus.PENDING_APPROVAL))
                .publishedCourses(courseRepository.countByStatus(CourseStatus.PUBLISHED))
                .build();
    }
}

