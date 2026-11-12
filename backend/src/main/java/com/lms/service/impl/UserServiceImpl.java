package com.lms.service.impl;

import com.lms.dto.user.SyncProfileRequest;
import com.lms.dto.user.UpdateProfileRequest;
import com.lms.dto.user.UserProfileDto;
import com.lms.entity.UserProfile;
import com.lms.enums.AccountStatus;
import com.lms.enums.Role;
import com.lms.exception.BadRequestException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.UserProfileRepository;
import com.lms.security.SecurityUtils;
import com.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserProfileRepository userProfileRepository;

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto getCurrentUserProfile() {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        return getUserProfileById(currentUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto getUserProfileById(UUID id) {
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for ID: " + id));
        return UserProfileDto.fromEntity(profile);
    }

    @Override
    @Transactional
    public UserProfileDto syncUserProfile(UUID userId, SyncProfileRequest request) {
        log.info("Synchronizing profile for authenticated Supabase user ID: {}", userId);

        UserProfile profile = userProfileRepository.findById(userId).orElse(null);

        if (profile == null) {
            // New user registration profile initialization
            Role assignedRole = Role.ROLE_STUDENT;
            if (request.getRequestedRole() != null) {
                String requested = request.getRequestedRole().trim().toUpperCase();
                // Security enforcement: Disallow untrusted client requests from ever granting ADMIN
                if (requested.contains("ADMIN")) {
                    log.warn("Security Alert: User {} attempted to self-assign ADMIN role. Defaulting to ROLE_STUDENT.", userId);
                    assignedRole = Role.ROLE_STUDENT;
                } else if (requested.contains("INSTRUCTOR")) {
                    assignedRole = Role.ROLE_INSTRUCTOR;
                }
            }

            profile = UserProfile.builder()
                    .id(userId)
                    .email(request.getEmail().trim().toLowerCase())
                    .fullName(request.getFullName())
                    .avatarUrl(request.getAvatarUrl())
                    .role(assignedRole)
                    .status(AccountStatus.ACTIVE)
                    .build();
        } else {
            // Existing profile - update mutable details while retaining verified role and status
            if (request.getFullName() != null && !request.getFullName().isBlank()) {
                profile.setFullName(request.getFullName().trim());
            }
            if (request.getAvatarUrl() != null) {
                profile.setAvatarUrl(request.getAvatarUrl().trim());
            }
        }

        UserProfile saved = userProfileRepository.save(profile);
        return UserProfileDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public UserProfileDto updateProfile(UUID userId, UpdateProfileRequest request) {
        UserProfile profile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for ID: " + userId));

        if (request.getFullName() != null) {
            profile.setFullName(request.getFullName().trim());
        }
        if (request.getAvatarUrl() != null) {
            profile.setAvatarUrl(request.getAvatarUrl().trim());
        }

        UserProfile updated = userProfileRepository.save(profile);
        return UserProfileDto.fromEntity(updated);
    }
}
