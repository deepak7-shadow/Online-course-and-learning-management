package com.lms.backend.controller;

import com.lms.backend.dto.common.ApiResponse;
import com.lms.backend.dto.course.CourseDetailDto;
import com.lms.backend.dto.course.CourseSummaryDto;
import com.lms.backend.dto.course.CreateCourseRequest;
import com.lms.backend.dto.course.UpdateCourseRequest;
import com.lms.backend.dto.lesson.CreateLessonRequest;
import com.lms.backend.dto.lesson.LessonDto;
import com.lms.backend.dto.module.CreateModuleRequest;
import com.lms.backend.dto.module.ModuleDto;
import com.lms.backend.dto.quiz.QuizDto;
import com.lms.backend.security.SecurityUtils;
import com.lms.backend.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/instructor")
@RequiredArgsConstructor
public class InstructorCourseController {

    private final CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<ApiResponse<List<CourseSummaryDto>>> getMyCourses() {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok(courseService.getInstructorCourses(instructorId)));
    }

    @PostMapping("/courses")
    public ResponseEntity<ApiResponse<CourseDetailDto>> createCourse(@Valid @RequestBody CreateCourseRequest request) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok("Course draft created successfully", courseService.createCourse(instructorId, request)));
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<ApiResponse<CourseDetailDto>> updateCourse(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCourseRequest request
    ) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok("Course updated successfully", courseService.updateCourse(id, instructorId, request)));
    }

    @PostMapping("/courses/{id}/submit")
    public ResponseEntity<ApiResponse<Void>> submitForReview(@PathVariable UUID id) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        courseService.submitCourseForReview(id, instructorId);
        return ResponseEntity.ok(ApiResponse.ok("Course submitted for admin review", null));
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<ApiResponse<ModuleDto>> addModule(
            @PathVariable UUID courseId,
            @Valid @RequestBody CreateModuleRequest request
    ) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok(courseService.addModule(courseId, instructorId, request)));
    }

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<ApiResponse<LessonDto>> addLesson(
            @PathVariable UUID moduleId,
            @Valid @RequestBody CreateLessonRequest request
    ) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok(courseService.addLesson(moduleId, instructorId, request)));
    }

    @PostMapping("/lessons/{lessonId}/quiz")
    public ResponseEntity<ApiResponse<QuizDto>> saveQuiz(
            @PathVariable UUID lessonId,
            @RequestBody QuizDto quizDto
    ) {
        UUID instructorId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok(courseService.createOrUpdateQuiz(lessonId, instructorId, quizDto)));
    }
}
