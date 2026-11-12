package com.lms.controller;

import com.lms.dto.common.ApiResponse;
import com.lms.dto.user.SyncProfileRequest;
import com.lms.dto.user.UpdateProfileRequest;
import com.lms.dto.user.UserProfileDto;
import com.lms.security.SecurityUtils;
import com.lms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileDto>> getCurrentUser() {
        UserProfileDto profile = userService.getCurrentUserProfile();
        return ResponseEntity.ok(ApiResponse.ok("Profile retrieved successfully", profile));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileDto>> updateCurrentUser(
            @Valid @RequestBody UpdateProfileRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        UserProfileDto updated = userService.updateProfile(currentUserId, request);
        return ResponseEntity.ok(ApiResponse.ok("Profile updated successfully", updated));
    }

    @PostMapping("/sync")
    public ResponseEntity<ApiResponse<UserProfileDto>> syncUserProfile(
            @Valid @RequestBody SyncProfileRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        UserProfileDto synced = userService.syncUserProfile(currentUserId, request);
        return ResponseEntity.ok(ApiResponse.ok("Profile synchronized with Supabase Auth", synced));
    }
}

