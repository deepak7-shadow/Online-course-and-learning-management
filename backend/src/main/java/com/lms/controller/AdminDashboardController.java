package com.lms.controller;

import com.lms.dto.admin.AdminStatsDto;
import com.lms.dto.common.ApiResponse;
import com.lms.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/stats")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ApiResponse<AdminStatsDto>> getOverviewStats() {
        AdminStatsDto stats = adminService.getAdminStats();
        return ResponseEntity.ok(ApiResponse.ok("Admin stats retrieved successfully", stats));
    }
}

