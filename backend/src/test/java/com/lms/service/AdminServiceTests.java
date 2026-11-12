package com.lms.service;

import com.lms.dto.admin.CourseApprovalRequest;
import com.lms.dto.admin.UserRoleUpdateRequest;
import com.lms.dto.admin.UserStatusUpdateRequest;
import com.lms.dto.user.UserProfileDto;
import com.lms.entity.Course;
import com.lms.entity.CourseApproval;
import com.lms.entity.UserProfile;
import com.lms.enums.AccountStatus;
import com.lms.enums.ApprovalDecision;
import com.lms.enums.CourseStatus;
import com.lms.enums.Role;
import com.lms.repository.CourseApprovalRepository;
import com.lms.repository.CourseRepository;
import com.lms.repository.UserProfileRepository;
import com.lms.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTests {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseApprovalRepository courseApprovalRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    @DisplayName("Admin can suspend user account")
    void testAdminSuspendsUser() {
        UUID userId = UUID.randomUUID();
        UserProfile user = UserProfile.builder()
                .id(userId)
                .email("user@test.com")
                .role(Role.ROLE_STUDENT)
                .status(AccountStatus.ACTIVE)
                .build();

        when(userProfileRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userProfileRepository.save(any(UserProfile.class))).thenAnswer(i -> i.getArgument(0));

        UserStatusUpdateRequest req = UserStatusUpdateRequest.builder()
                .status(AccountStatus.SUSPENDED)
                .reason("Terms of Service violation")
                .build();

        UserProfileDto result = adminService.updateUserStatus(userId, req);

        assertNotNull(result);
        assertEquals(AccountStatus.SUSPENDED, result.getStatus());
        verify(userProfileRepository).save(user);
    }

    @Test
    @DisplayName("Admin can approve pending course")
    void testAdminApprovesCourse() {
        UUID courseId = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();

        Course course = Course.builder()
                .id(courseId)
                .title("Java Spring Boot Masterclass")
                .status(CourseStatus.PENDING_APPROVAL)
                .build();

        UserProfile admin = UserProfile.builder()
                .id(adminId)
                .role(Role.ROLE_ADMIN)
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(userProfileRepository.findById(adminId)).thenReturn(Optional.of(admin));
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArgument(0));

        CourseApprovalRequest request = CourseApprovalRequest.builder()
                .decision(ApprovalDecision.APPROVED)
                .comments("Meets all curriculum quality standards")
                .build();

        var approved = adminService.approveOrRejectCourse(courseId, adminId, request);

        assertNotNull(approved);
        assertEquals(CourseStatus.PUBLISHED, approved.getStatus());
        verify(courseApprovalRepository).save(any(CourseApproval.class));
    }
}
