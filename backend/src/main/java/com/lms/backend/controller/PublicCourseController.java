package com.lms.backend.controller;

import com.lms.backend.dto.common.ApiResponse;
import com.lms.backend.dto.common.PageResponse;
import com.lms.backend.dto.course.CourseDetailDto;
import com.lms.backend.dto.course.CourseSummaryDto;
import com.lms.backend.enums.CourseLevel;
import com.lms.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/courses")
@RequiredArgsConstructor
public class PublicCourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CourseSummaryDto>>> searchCourses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) CourseLevel level,
            @RequestParam(required = false) String query,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.ok(PageResponse.of(courseService.searchCourses(category, level, query, pageable))));
    }

    @GetMapping("/{idOrSlug}")
    public ResponseEntity<ApiResponse<CourseDetailDto>> getCourseDetail(@PathVariable String idOrSlug) {
        return ResponseEntity.ok(ApiResponse.ok(courseService.getCourseDetail(idOrSlug)));
    }
}
