package com.lms.backend.controller;

import com.lms.backend.dto.common.ApiResponse;
import com.lms.backend.dto.common.PageResponse;
import com.lms.backend.dto.course.CourseDetailDto;
import com.lms.backend.dto.learning.EnrollmentDto;
import com.lms.backend.dto.lesson.LessonProgressRequest;
import com.lms.backend.dto.quiz.QuizResultDto;
import com.lms.backend.dto.quiz.QuizSubmitRequest;
import com.lms.backend.security.SecurityUtils;
import com.lms.backend.service.LearningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/learning")
@RequiredArgsConstructor
public class StudentLearningController {

    private final LearningService learningService;

    @PostMapping("/courses/{courseId}/enroll")
    public ResponseEntity<ApiResponse<EnrollmentDto>> enroll(@PathVariable UUID courseId) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok("Enrolled successfully", learningService.enrollInCourse(userId, courseId)));
    }

    @GetMapping("/enrollments")
    public ResponseEntity<ApiResponse<PageResponse<EnrollmentDto>>> getMyEnrollments(Pageable pageable) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok(PageResponse.of(learningService.getStudentEnrollments(userId, pageable))));
    }

    @GetMapping("/courses/{courseId}/player")
    public ResponseEntity<ApiResponse<CourseDetailDto>> getCoursePlayer(@PathVariable UUID courseId) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok(learningService.getCoursePlayer(userId, courseId)));
    }

    @PostMapping("/lessons/{lessonId}/progress")
    public ResponseEntity<ApiResponse<Void>> updateProgress(
            @PathVariable UUID lessonId,
            @RequestBody LessonProgressRequest request
    ) {
        UUID userId = SecurityUtils.getCurrentUserId();
        learningService.updateLessonProgress(userId, lessonId, request);
        return ResponseEntity.ok(ApiResponse.ok("Progress updated", null));
    }

    @PostMapping("/quizzes/{quizId}/submit")
    public ResponseEntity<ApiResponse<QuizResultDto>> submitQuiz(
            @PathVariable UUID quizId,
            @Valid @RequestBody QuizSubmitRequest request
    ) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok(learningService.submitQuiz(userId, quizId, request)));
    }

    @GetMapping("/wishlist")
    public ResponseEntity<ApiResponse<List<UUID>>> getWishlist() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok(learningService.getWishlist(userId)));
    }

    @PostMapping("/wishlist/{courseId}")
    public ResponseEntity<ApiResponse<Void>> toggleWishlist(@PathVariable UUID courseId) {
        UUID userId = SecurityUtils.getCurrentUserId();
        learningService.toggleWishlist(userId, courseId);
        return ResponseEntity.ok(ApiResponse.ok("Wishlist toggled", null));
    }
}
