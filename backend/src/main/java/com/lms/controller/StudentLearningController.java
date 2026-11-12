package com.lms.controller;

import com.lms.dto.common.ApiResponse;
import com.lms.dto.common.PageResponse;
import com.lms.dto.learning.*;
import com.lms.security.SecurityUtils;
import com.lms.service.LearningService;
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
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class StudentLearningController {

    private final LearningService learningService;

    // ─── Enrollment ──────────────────────────────────────────────────────────

    @PostMapping("/courses/{courseId}/enroll")
    public ResponseEntity<ApiResponse<EnrollmentDto.Response>> enroll(@PathVariable UUID courseId) {
        UUID studentId = SecurityUtils.getCurrentUserId();
        EnrollmentDto.Response enrollment = learningService.enrollStudent(studentId, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Enrolled successfully", enrollment));
    }

    @GetMapping("/enrollments")
    public ResponseEntity<ApiResponse<PageResponse<EnrollmentDto.Response>>> getMyEnrollments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        UUID studentId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "enrolledAt"));
        return ResponseEntity.ok(ApiResponse.ok("Enrollments retrieved", learningService.getStudentEnrollments(studentId, pageable)));
    }

    @GetMapping("/courses/{courseId}/progress")
    public ResponseEntity<ApiResponse<EnrollmentDto.ProgressResponse>> getCourseProgress(@PathVariable UUID courseId) {
        UUID studentId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok("Progress retrieved", learningService.getCourseProgress(studentId, courseId)));
    }

    // ─── Lesson Progress ─────────────────────────────────────────────────────

    @PostMapping("/lessons/{lessonId}/complete")
    public ResponseEntity<ApiResponse<Void>> markLessonComplete(@PathVariable UUID lessonId) {
        UUID studentId = SecurityUtils.getCurrentUserId();
        learningService.markLessonComplete(studentId, lessonId);
        return ResponseEntity.ok(ApiResponse.ok("Lesson marked complete", null));
    }

    // ─── Quiz ─────────────────────────────────────────────────────────────────

    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<ApiResponse<QuizDto.Response>> getQuiz(@PathVariable UUID quizId) {
        return ResponseEntity.ok(ApiResponse.ok("Quiz retrieved", learningService.getQuiz(quizId)));
    }

    @PostMapping("/quizzes/{quizId}/submit")
    public ResponseEntity<ApiResponse<QuizDto.AttemptResult>> submitQuiz(
            @PathVariable UUID quizId,
            @RequestBody QuizDto.SubmitRequest request) {
        UUID studentId = SecurityUtils.getCurrentUserId();
        QuizDto.AttemptResult result = learningService.submitQuiz(studentId, quizId, request);
        return ResponseEntity.ok(ApiResponse.ok("Quiz submitted", result));
    }

    @GetMapping("/quizzes/{quizId}/attempts")
    public ResponseEntity<ApiResponse<List<QuizDto.AttemptResult>>> getMyAttempts(@PathVariable UUID quizId) {
        UUID studentId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok("Attempts retrieved", learningService.getMyAttempts(studentId, quizId)));
    }

    // ─── Certificates ────────────────────────────────────────────────────────

    @PostMapping("/courses/{courseId}/certificate")
    public ResponseEntity<ApiResponse<CertificateDto>> issueCertificate(@PathVariable UUID courseId) {
        UUID studentId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok("Certificate issued", learningService.issueCertificate(studentId, courseId)));
    }

    @GetMapping("/certificates")
    public ResponseEntity<ApiResponse<List<CertificateDto>>> getMyCertificates() {
        UUID studentId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok("Certificates retrieved", learningService.getStudentCertificates(studentId)));
    }

    // ─── Reviews ─────────────────────────────────────────────────────────────

    @PostMapping("/courses/{courseId}/review")
    public ResponseEntity<ApiResponse<ReviewDto.Response>> addReview(
            @PathVariable UUID courseId,
            @RequestBody ReviewDto.Request request) {
        UUID studentId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok("Review submitted", learningService.addOrUpdateReview(studentId, courseId, request)));
    }

    @GetMapping("/courses/{courseId}/reviews")
    public ResponseEntity<ApiResponse<PageResponse<ReviewDto.Response>>> getCourseReviews(
            @PathVariable UUID courseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.ok(ApiResponse.ok("Reviews retrieved", learningService.getCourseReviews(courseId, pageable)));
    }

    // ─── Wishlist ─────────────────────────────────────────────────────────────

    @PostMapping("/wishlist/{courseId}")
    public ResponseEntity<ApiResponse<Void>> addToWishlist(@PathVariable UUID courseId) {
        UUID studentId = SecurityUtils.getCurrentUserId();
        learningService.addToWishlist(studentId, courseId);
        return ResponseEntity.ok(ApiResponse.ok("Added to wishlist", null));
    }

    @DeleteMapping("/wishlist/{courseId}")
    public ResponseEntity<ApiResponse<Void>> removeFromWishlist(@PathVariable UUID courseId) {
        UUID studentId = SecurityUtils.getCurrentUserId();
        learningService.removeFromWishlist(studentId, courseId);
        return ResponseEntity.ok(ApiResponse.ok("Removed from wishlist", null));
    }

    @GetMapping("/wishlist")
    public ResponseEntity<ApiResponse<PageResponse<EnrollmentDto.Response>>> getWishlist(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        UUID studentId = SecurityUtils.getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.ok("Wishlist retrieved", learningService.getWishlist(studentId, pageable)));
    }
}

