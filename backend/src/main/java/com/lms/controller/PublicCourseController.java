package com.lms.controller;

import com.lms.dto.common.ApiResponse;
import com.lms.dto.common.PageResponse;
import com.lms.dto.course.CourseDto;
import com.lms.dto.course.ModuleDto;
import com.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class PublicCourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CourseDto.Response>>> listCourses(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) String level,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        return ResponseEntity.ok(ApiResponse.ok("Courses retrieved", courseService.getPublishedCourses(keyword, categoryId, level, pageable)));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<CourseDto.Response>> getCourseBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.ok("Course retrieved", courseService.getCourseBySlug(slug)));
    }

    @GetMapping("/{courseId}/modules")
    public ResponseEntity<ApiResponse<List<ModuleDto.Response>>> getCourseModules(@PathVariable UUID courseId) {
        return ResponseEntity.ok(ApiResponse.ok("Modules retrieved", courseService.getCourseModules(courseId)));
    }
}

