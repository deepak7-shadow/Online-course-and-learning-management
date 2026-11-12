package com.lms.controller;

import com.lms.dto.common.ApiResponse;
import com.lms.dto.common.PageResponse;
import com.lms.dto.course.CourseDto;
import com.lms.dto.course.LessonDto;
import com.lms.dto.course.ModuleDto;
import com.lms.security.SecurityUtils;
import com.lms.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/instructor")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('INSTRUCTOR','ADMIN')")
public class InstructorCourseController {

    private final CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<ApiResponse<PageResponse<CourseDto.Response>>> getMyCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.ok(ApiResponse.ok("Courses retrieved", courseService.getInstructorCourses(instructorId, pageable)));
    }

    @PostMapping("/courses")
    public ResponseEntity<ApiResponse<CourseDto.Response>> createCourse(
            @Valid @RequestBody CourseDto.CreateRequest request) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        CourseDto.Response created = courseService.createCourse(instructorId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Course created", created));
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<ApiResponse<CourseDto.Response>> updateCourse(
            @PathVariable UUID courseId,
            @RequestBody CourseDto.UpdateRequest request) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok("Course updated", courseService.updateCourse(courseId, instructorId, request)));
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable UUID courseId) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        courseService.deleteCourse(courseId, instructorId);
        return ResponseEntity.ok(ApiResponse.ok("Course deleted", null));
    }

    @PostMapping("/courses/{courseId}/submit")
    public ResponseEntity<ApiResponse<CourseDto.Response>> submitForReview(@PathVariable UUID courseId) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok("Course submitted for review", courseService.submitForApproval(courseId, instructorId)));
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<ApiResponse<List<ModuleDto.Response>>> getModules(@PathVariable UUID courseId) {
        return ResponseEntity.ok(ApiResponse.ok("Modules retrieved", courseService.getCourseModules(courseId)));
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<ApiResponse<ModuleDto.Response>> addModule(
            @PathVariable UUID courseId,
            @Valid @RequestBody ModuleDto.Request request) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Module added", courseService.addModule(courseId, instructorId, request)));
    }

    @PutMapping("/modules/{moduleId}")
    public ResponseEntity<ApiResponse<ModuleDto.Response>> updateModule(
            @PathVariable UUID moduleId,
            @RequestBody ModuleDto.Request request) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok("Module updated", courseService.updateModule(moduleId, instructorId, request)));
    }

    @DeleteMapping("/modules/{moduleId}")
    public ResponseEntity<ApiResponse<Void>> deleteModule(@PathVariable UUID moduleId) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        courseService.deleteModule(moduleId, instructorId);
        return ResponseEntity.ok(ApiResponse.ok("Module deleted", null));
    }

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<ApiResponse<LessonDto.Response>> addLesson(
            @PathVariable UUID moduleId,
            @Valid @RequestBody LessonDto.Request request) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Lesson added", courseService.addLesson(moduleId, instructorId, request)));
    }

    @PutMapping("/lessons/{lessonId}")
    public ResponseEntity<ApiResponse<LessonDto.Response>> updateLesson(
            @PathVariable UUID lessonId,
            @RequestBody LessonDto.Request request) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok("Lesson updated", courseService.updateLesson(lessonId, instructorId, request)));
    }

    @DeleteMapping("/lessons/{lessonId}")
    public ResponseEntity<ApiResponse<Void>> deleteLesson(@PathVariable UUID lessonId) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        courseService.deleteLesson(lessonId, instructorId);
        return ResponseEntity.ok(ApiResponse.ok("Lesson deleted", null));
    }
}

