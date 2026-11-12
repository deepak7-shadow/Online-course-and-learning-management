package com.lms.service;

import com.lms.dto.user.SyncProfileRequest;
import com.lms.dto.user.UserProfileDto;
import com.lms.entity.UserProfile;
import com.lms.enums.AccountStatus;
import com.lms.enums.Role;
import com.lms.repository.UserProfileRepository;
import com.lms.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UUID userId;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Syncing new profile with requested ADMIN role should be downgraded to ROLE_STUDENT")
    void testDisallowAdminSelfAssignment() {
        when(userProfileRepository.findById(userId)).thenReturn(Optional.empty());
        when(userProfileRepository.save(any(UserProfile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SyncProfileRequest request = SyncProfileRequest.builder()
                .email("hacker@test.com")
                .fullName("Untrusted User")
                .requestedRole("ROLE_ADMIN")
                .build();

        UserProfileDto result = userService.syncUserProfile(userId, request);

        assertNotNull(result);
        assertEquals(Role.ROLE_STUDENT, result.getRole(), "Client requested ADMIN must be forced to ROLE_STUDENT");
        assertEquals(AccountStatus.ACTIVE, result.getStatus());
        verify(userProfileRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    @DisplayName("Syncing new instructor profile should assign ROLE_INSTRUCTOR")
    void testAllowInstructorRoleOnRegistration() {
        when(userProfileRepository.findById(userId)).thenReturn(Optional.empty());
        when(userProfileRepository.save(any(UserProfile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SyncProfileRequest request = SyncProfileRequest.builder()
                .email("teacher@test.com")
                .fullName("John Instructor")
                .requestedRole("INSTRUCTOR")
                .build();

        UserProfileDto result = userService.syncUserProfile(userId, request);

        assertNotNull(result);
        assertEquals(Role.ROLE_INSTRUCTOR, result.getRole());
    }
}
