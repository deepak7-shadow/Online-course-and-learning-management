package com.lms.backend.service.impl;

import com.lms.backend.dto.user.UpdateProfileRequest;
import com.lms.backend.dto.user.UserProfileDto;
import com.lms.backend.entity.UserProfile;
import com.lms.backend.exception.ResourceNotFoundException;
import com.lms.backend.repository.UserProfileRepository;
import com.lms.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserProfileRepository userProfileRepository;

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto getCurrentUserProfile(UUID userId) {
        UserProfile profile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for id: " + userId));
        return mapToDto(profile);
    }

    @Override
    @Transactional
    public UserProfileDto updateProfile(UUID userId, UpdateProfileRequest request) {
        UserProfile profile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for id: " + userId));

        if (request.getFullName() != null) profile.setFullName(request.getFullName());
        if (request.getAvatarUrl() != null) profile.setAvatarUrl(request.getAvatarUrl());
        if (request.getBio() != null) profile.setBio(request.getBio());
        if (request.getHeadline() != null) profile.setHeadline(request.getHeadline());

        return mapToDto(userProfileRepository.save(profile));
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto getInstructorProfile(UUID instructorId) {
        UserProfile profile = userProfileRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found for id: " + instructorId));
        return mapToDto(profile);
    }

    private UserProfileDto mapToDto(UserProfile profile) {
        return UserProfileDto.builder()
                .id(profile.getId())
                .email(profile.getEmail())
                .fullName(profile.getFullName())
                .avatarUrl(profile.getAvatarUrl())
                .role(profile.getRole())
                .accountStatus(profile.getAccountStatus())
                .bio(profile.getBio())
                .headline(profile.getHeadline())
                .createdAt(profile.getCreatedAt())
                .build();
    }
}
