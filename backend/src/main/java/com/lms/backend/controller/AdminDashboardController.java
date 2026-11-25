package com.lms.backend.controller;

import com.lms.backend.dto.admin.DashboardMetricsDto;
import com.lms.backend.dto.common.ApiResponse;
import com.lms.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardMetricsDto>> getDashboardMetrics() {
        return ResponseEntity.ok(ApiResponse.ok(adminService.getDashboardMetrics()));
    }
}
