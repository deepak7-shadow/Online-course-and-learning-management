package com.lms.service;

import com.lms.dto.common.PageResponse;
import com.lms.dto.learning.CertificateDto;
import com.lms.dto.learning.EnrollmentDto;
import com.lms.dto.learning.QuizDto;
import com.lms.dto.learning.ReviewDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface LearningService {

    // Enrollment
    EnrollmentDto.Response enrollStudent(UUID studentId, UUID courseId);
    PageResponse<EnrollmentDto.Response> getStudentEnrollments(UUID studentId, Pageable pageable);
    EnrollmentDto.ProgressResponse getCourseProgress(UUID studentId, UUID courseId);

    // Lesson progress
    void markLessonComplete(UUID studentId, UUID lessonId);

    // Quiz
    QuizDto.Response getQuiz(UUID quizId);
    QuizDto.AttemptResult submitQuiz(UUID studentId, UUID quizId, QuizDto.SubmitRequest request);
    List<QuizDto.AttemptResult> getMyAttempts(UUID studentId, UUID quizId);

    // Certificate
    CertificateDto issueCertificate(UUID studentId, UUID courseId);
    CertificateDto getCertificateByCode(String code);
    List<CertificateDto> getStudentCertificates(UUID studentId);

    // Reviews
    ReviewDto.Response addOrUpdateReview(UUID studentId, UUID courseId, ReviewDto.Request request);
    PageResponse<ReviewDto.Response> getCourseReviews(UUID courseId, Pageable pageable);

    // Wishlist
    void addToWishlist(UUID studentId, UUID courseId);
    void removeFromWishlist(UUID studentId, UUID courseId);
    PageResponse<EnrollmentDto.Response> getWishlist(UUID studentId, Pageable pageable);
}

