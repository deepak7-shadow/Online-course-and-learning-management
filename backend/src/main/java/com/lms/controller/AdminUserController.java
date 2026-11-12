package com.lms.controller;

import com.lms.dto.admin.UserRoleUpdateRequest;
import com.lms.dto.admin.UserStatusUpdateRequest;
import com.lms.dto.common.ApiResponse;
import com.lms.dto.common.PageResponse;
import com.lms.dto.user.UserProfileDto;
import com.lms.enums.AccountStatus;
import com.lms.enums.Role;
import com.lms.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserProfileDto>>> listUsers(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) AccountStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        PageResponse<UserProfileDto> users = adminService.getAllUsers(query, role, status, pageable);
        return ResponseEntity.ok(ApiResponse.ok("Users retrieved successfully", users));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileDto>> getUserById(@PathVariable UUID userId) {
        UserProfileDto user = adminService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.ok("User details retrieved successfully", user));
    }

    @PatchMapping("/{userId}/status")
    public ResponseEntity<ApiResponse<UserProfileDto>> updateUserStatus(
            @PathVariable UUID userId,
            @Valid @RequestBody UserStatusUpdateRequest request) {
        UserProfileDto updated = adminService.updateUserStatus(userId, request);
        return ResponseEntity.ok(ApiResponse.ok("User status updated successfully", updated));
    }

    @PatchMapping("/{userId}/role")
    public ResponseEntity<ApiResponse<UserProfileDto>> updateUserRole(
            @PathVariable UUID userId,
            @Valid @RequestBody UserRoleUpdateRequest request) {
        UserProfileDto updated = adminService.updateUserRole(userId, request);
        return ResponseEntity.ok(ApiResponse.ok("User role updated successfully", updated));
    }
}
