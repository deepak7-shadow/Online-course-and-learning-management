package com.lms.backend.controller;

import com.lms.backend.dto.admin.CourseApprovalRequest;
import com.lms.backend.dto.common.ApiResponse;
import com.lms.backend.dto.common.PageResponse;
import com.lms.backend.dto.course.CourseSummaryDto;
import com.lms.backend.security.SecurityUtils;
import com.lms.backend.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/courses")
@RequiredArgsConstructor
public class AdminCourseController {

    private final AdminService adminService;

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<PageResponse<CourseSummaryDto>>> getPendingCourses(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(PageResponse.of(adminService.getPendingCourses(pageable))));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<Void>> approveCourse(
            @PathVariable UUID id,
            @Valid @RequestBody CourseApprovalRequest request
    ) {
        UUID adminId = SecurityUtils.getCurrentUserId();
        adminService.approveOrRejectCourse(id, adminId, request);
        return ResponseEntity.ok(ApiResponse.ok("Course review decision recorded", null));
    }
}
