package com.lms.service;

import com.lms.dto.user.SyncProfileRequest;
import com.lms.dto.user.UpdateProfileRequest;
import com.lms.dto.user.UserProfileDto;

import java.util.UUID;

public interface UserService {

    UserProfileDto getCurrentUserProfile();

    UserProfileDto getUserProfileById(UUID id);

    UserProfileDto syncUserProfile(UUID userId, SyncProfileRequest request);

    UserProfileDto updateProfile(UUID userId, UpdateProfileRequest request);
}

