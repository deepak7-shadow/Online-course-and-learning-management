package com.lms.controller;

import com.lms.dto.admin.AdminCourseDto;
import com.lms.dto.admin.CourseApprovalRequest;
import com.lms.dto.common.ApiResponse;
import com.lms.dto.common.PageResponse;
import com.lms.security.SecurityUtils;
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
@RequestMapping("/api/v1/admin/courses")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCourseController {

    private final AdminService adminService;

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<PageResponse<AdminCourseDto>>> getPendingCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        PageResponse<AdminCourseDto> pending = adminService.getPendingCourses(pageable);
        return ResponseEntity.ok(ApiResponse.ok("Pending courses retrieved successfully", pending));
    }

    @PostMapping("/{courseId}/approval")
    public ResponseEntity<ApiResponse<AdminCourseDto>> processCourseApproval(
            @PathVariable UUID courseId,
            @Valid @RequestBody CourseApprovalRequest request) {

        UUID adminId = SecurityUtils.getCurrentUserId();
        AdminCourseDto result = adminService.approveOrRejectCourse(courseId, adminId, request);
        return ResponseEntity.ok(ApiResponse.ok("Course approval decision recorded successfully", result));
    }
}
