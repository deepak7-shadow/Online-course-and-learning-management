package com.lms.backend.service;

import com.lms.backend.dto.course.CourseDetailDto;
import com.lms.backend.dto.learning.CertificateDto;
import com.lms.backend.dto.learning.EnrollmentDto;
import com.lms.backend.dto.lesson.LessonProgressRequest;
import com.lms.backend.dto.quiz.QuizResultDto;
import com.lms.backend.dto.quiz.QuizSubmitRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface LearningService {
    EnrollmentDto enrollInCourse(UUID userId, UUID courseId);
    Page<EnrollmentDto> getStudentEnrollments(UUID userId, Pageable pageable);
    CourseDetailDto getCoursePlayer(UUID userId, UUID courseId);
    void updateLessonProgress(UUID userId, UUID lessonId, LessonProgressRequest request);
    QuizResultDto submitQuiz(UUID userId, UUID quizId, QuizSubmitRequest request);
    CertificateDto getCertificateByCode(String code);
    List<CertificateDto> getStudentCertificates(UUID userId);
    List<UUID> getWishlist(UUID userId);
    void toggleWishlist(UUID userId, UUID courseId);
}
