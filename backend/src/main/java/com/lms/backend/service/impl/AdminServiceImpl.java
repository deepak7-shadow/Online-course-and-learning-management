package com.lms.backend.service.impl;

import com.lms.backend.dto.admin.CourseApprovalRequest;
import com.lms.backend.dto.admin.DashboardMetricsDto;
import com.lms.backend.dto.course.CourseSummaryDto;
import com.lms.backend.dto.user.UserProfileDto;
import com.lms.backend.entity.Course;
import com.lms.backend.entity.CourseApproval;
import com.lms.backend.entity.UserProfile;
import com.lms.backend.enums.AccountStatus;
import com.lms.backend.enums.ApprovalDecision;
import com.lms.backend.enums.CourseStatus;
import com.lms.backend.enums.EnrollmentStatus;
import com.lms.backend.enums.Role;
import com.lms.backend.exception.ResourceNotFoundException;
import com.lms.backend.repository.CourseApprovalRepository;
import com.lms.backend.repository.CourseRepository;
import com.lms.backend.repository.EnrollmentRepository;
import com.lms.backend.repository.UserProfileRepository;
import com.lms.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserProfileRepository userProfileRepository;
    private final CourseRepository courseRepository;
    private final CourseApprovalRepository courseApprovalRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardMetricsDto getDashboardMetrics() {
        return DashboardMetricsDto.builder()
                .totalStudents(userProfileRepository.countByRole(Role.STUDENT))
                .totalInstructors(userProfileRepository.countByRole(Role.INSTRUCTOR))
                .totalCourses(courseRepository.count())
                .pendingCourseApprovals(courseRepository.countByStatus(CourseStatus.SUBMITTED))
                .totalEnrollments(enrollmentRepository.count())
                .completedEnrollments(enrollmentRepository.countByStatus(EnrollmentStatus.COMPLETED))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserProfileDto> getUsers(Role roleFilter, Pageable pageable) {
        Page<UserProfile> page;
        if (roleFilter != null) {
            page = userProfileRepository.findByRole(roleFilter, pageable);
        } else {
            page = userProfileRepository.findAll(pageable);
        }
        return page.map(p -> UserProfileDto.builder()
                .id(p.getId())
                .email(p.getEmail())
                .fullName(p.getFullName())
                .avatarUrl(p.getAvatarUrl())
                .role(p.getRole())
                .accountStatus(p.getAccountStatus())
                .bio(p.getBio())
                .headline(p.getHeadline())
                .createdAt(p.getCreatedAt())
                .build());
    }

    @Override
    @Transactional
    public UserProfileDto updateUserStatus(UUID userId, AccountStatus status) {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setAccountStatus(status);
        user = userProfileRepository.save(user);
        return UserProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .role(user.getRole())
                .accountStatus(user.getAccountStatus())
                .bio(user.getBio())
                .headline(user.getHeadline())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public UserProfileDto updateUserRole(UUID userId, Role role) {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setRole(role);
        user = userProfileRepository.save(user);
        return UserProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .role(user.getRole())
                .accountStatus(user.getAccountStatus())
                .bio(user.getBio())
                .headline(user.getHeadline())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseSummaryDto> getPendingCourses(Pageable pageable) {
        return courseRepository.findByStatus(CourseStatus.SUBMITTED, pageable)
                .map(this::mapToSummary);
    }

    @Override
    @Transactional
    public void approveOrRejectCourse(UUID courseId, UUID adminId, CourseApprovalRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));
        UserProfile admin = userProfileRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin profile not found: " + adminId));

        if (request.getDecision() == ApprovalDecision.APPROVED) {
            course.setStatus(CourseStatus.APPROVED);
        } else {
            course.setStatus(CourseStatus.REJECTED);
        }
        courseRepository.save(course);

        CourseApproval approval = CourseApproval.builder()
                .course(course)
                .reviewedBy(admin)
                .decision(request.getDecision())
                .comments(request.getComments())
                .build();
        courseApprovalRepository.save(approval);
    }

    private CourseSummaryDto mapToSummary(Course course) {
        return CourseSummaryDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .slug(course.getSlug())
                .subtitle(course.getSubtitle())
                .categoryName(course.getCategory() != null ? course.getCategory().getName() : "General")
                .instructorName(course.getInstructor() != null ? course.getInstructor().getFullName() : "Instructor")
                .price(course.getPrice())
                .level(course.getLevel())
                .status(course.getStatus())
                .thumbnailUrl(course.getThumbnailUrl())
                .build();
    }
}
