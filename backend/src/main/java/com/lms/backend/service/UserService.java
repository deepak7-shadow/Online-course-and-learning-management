package com.lms.backend.service;

import com.lms.backend.dto.user.UpdateProfileRequest;
import com.lms.backend.dto.user.UserProfileDto;

import java.util.UUID;

public interface UserService {
    UserProfileDto getCurrentUserProfile(UUID userId);
    UserProfileDto updateProfile(UUID userId, UpdateProfileRequest request);
    UserProfileDto getInstructorProfile(UUID instructorId);
}
