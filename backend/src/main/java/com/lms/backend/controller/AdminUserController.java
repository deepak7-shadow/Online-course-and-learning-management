package com.lms.backend.controller;

import com.lms.backend.dto.admin.UpdateUserRoleRequest;
import com.lms.backend.dto.admin.UpdateUserStatusRequest;
import com.lms.backend.dto.common.ApiResponse;
import com.lms.backend.dto.common.PageResponse;
import com.lms.backend.dto.user.UserProfileDto;
import com.lms.backend.enums.Role;
import com.lms.backend.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserProfileDto>>> getUsers(
            @RequestParam(required = false) Role role,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.ok(PageResponse.of(adminService.getUsers(role, pageable))));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<UserProfileDto>> updateUserStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserStatusRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok(adminService.updateUserStatus(id, request.getStatus())));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<ApiResponse<UserProfileDto>> updateUserRole(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRoleRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok(adminService.updateUserRole(id, request.getRole())));
    }
}
