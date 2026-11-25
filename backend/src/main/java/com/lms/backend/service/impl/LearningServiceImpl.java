package com.lms.backend.service.impl;

import com.lms.backend.dto.course.CourseDetailDto;
import com.lms.backend.dto.course.CourseSummaryDto;
import com.lms.backend.dto.learning.CertificateDto;
import com.lms.backend.dto.learning.EnrollmentDto;
import com.lms.backend.dto.lesson.LessonDto;
import com.lms.backend.dto.lesson.LessonProgressRequest;
import com.lms.backend.dto.module.ModuleDto;
import com.lms.backend.dto.quiz.QuizResultDto;
import com.lms.backend.dto.quiz.QuizSubmitRequest;
import com.lms.backend.entity.*;
import com.lms.backend.enums.EnrollmentStatus;
import com.lms.backend.exception.BadRequestException;
import com.lms.backend.exception.ResourceNotFoundException;
import com.lms.backend.repository.*;
import com.lms.backend.service.CourseService;
import com.lms.backend.service.LearningService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearningServiceImpl implements LearningService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserProfileRepository userProfileRepository;
    private final LessonRepository lessonRepository;
    private final LessonProgressRepository lessonProgressRepository;
    private final QuizRepository quizRepository;
    private final CertificateRepository certificateRepository;
    private final WishlistRepository wishlistRepository;
    private final CourseService courseService;

    @Override
    @Transactional
    public EnrollmentDto enrollInCourse(UUID userId, UUID courseId) {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        Optional<Enrollment> existing = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
        if (existing.isPresent()) {
            return mapToDto(existing.get());
        }

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .progressPercentage(BigDecimal.ZERO)
                .status(EnrollmentStatus.ACTIVE)
                .enrolledAt(Instant.now())
                .build();
        enrollment = enrollmentRepository.save(enrollment);

        return mapToDto(enrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnrollmentDto> getStudentEnrollments(UUID userId, Pageable pageable) {
        return enrollmentRepository.findByUserId(userId, pageable)
                .map(this::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDetailDto getCoursePlayer(UUID userId, UUID courseId) {
        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new BadRequestException("You are not enrolled in this course"));

        CourseDetailDto detail = courseService.getCourseDetail(courseId.toString());

        // Attach completion progress for each lesson
        List<LessonProgress> progresses = lessonProgressRepository.findByEnrollmentId(enrollment.getId());
        var completedMap = progresses.stream()
                .filter(LessonProgress::getIsCompleted)
                .collect(Collectors.toMap(p -> p.getLesson().getId(), p -> true, (a, b) -> a));

        for (ModuleDto module : detail.getModules()) {
            for (LessonDto lesson : module.getLessons()) {
                lesson.setIsCompleted(completedMap.getOrDefault(lesson.getId(), false));
            }
        }
        return detail;
    }

    @Override
    @Transactional
    public void updateLessonProgress(UUID userId, UUID lessonId, LessonProgressRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found: " + lessonId));
        UUID courseId = lesson.getModule().getCourse().getId();

        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new BadRequestException("Not enrolled in course"));

        LessonProgress progress = lessonProgressRepository.findByEnrollmentIdAndLessonId(enrollment.getId(), lessonId)
                .orElseGet(() -> LessonProgress.builder()
                        .enrollment(enrollment)
                        .lesson(lesson)
                        .build());

        if (request.getIsCompleted() != null) {
            progress.setIsCompleted(request.getIsCompleted());
            if (request.getIsCompleted()) {
                progress.setCompletedAt(Instant.now());
            }
        }
        if (request.getLastWatchedSecond() != null) {
            progress.setLastWatchedSecond(request.getLastWatchedSecond());
        }
        lessonProgressRepository.save(progress);

        // Recalculate enrollment total progress percentage
        int totalLessons = lesson.getModule().getCourse().getModules().stream()
                .mapToInt(m -> m.getLessons().size())
                .sum();
        long completedCount = lessonProgressRepository.countByEnrollmentIdAndIsCompletedTrue(enrollment.getId());

        if (totalLessons > 0) {
            BigDecimal progressPct = BigDecimal.valueOf((double) completedCount / totalLessons * 100.0)
                    .setScale(2, RoundingMode.HALF_UP);
            enrollment.setProgressPercentage(progressPct);

            if (progressPct.compareTo(BigDecimal.valueOf(100.0)) >= 0 && enrollment.getStatus() != EnrollmentStatus.COMPLETED) {
                enrollment.setStatus(EnrollmentStatus.COMPLETED);
                enrollment.setCompletedAt(Instant.now());
                generateCertificate(enrollment);
            }
            enrollmentRepository.save(enrollment);
        }
    }

    @Override
    @Transactional
    public QuizResultDto submitQuiz(UUID userId, UUID quizId, QuizSubmitRequest request) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found: " + quizId));

        int totalQuestions = quiz.getQuestions().size();
        int correctCount = 0;

        for (Question question : quiz.getQuestions()) {
            UUID selectedOptionId = request.getAnswers().get(question.getId());
            if (selectedOptionId != null) {
                boolean isCorrect = question.getOptions().stream()
                        .anyMatch(opt -> opt.getId().equals(selectedOptionId) && Boolean.TRUE.equals(opt.getIsCorrect()));
                if (isCorrect) {
                    correctCount++;
                }
            }
        }

        double score = totalQuestions > 0 ? ((double) correctCount / totalQuestions) * 100.0 : 0.0;
        boolean passed = score >= quiz.getPassingPercentage();

        if (passed) {
            updateLessonProgress(userId, quiz.getLesson().getId(), LessonProgressRequest.builder().isCompleted(true).build());
        }

        return QuizResultDto.builder()
                .score(BigDecimal.valueOf(score).setScale(2, RoundingMode.HALF_UP))
                .passed(passed)
                .totalQuestions(totalQuestions)
                .correctAnswers(correctCount)
                .message(passed ? "Congratulations! You passed the quiz." : "You did not achieve the passing grade. Please review and try again.")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CertificateDto getCertificateByCode(String code) {
        Certificate cert = certificateRepository.findByCertificateCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found for code: " + code));
        return mapToCertificateDto(cert);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDto> getStudentCertificates(UUID userId) {
        return certificateRepository.findByEnrollmentUserId(userId).stream()
                .map(this::mapToCertificateDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UUID> getWishlist(UUID userId) {
        return wishlistRepository.findByUserId(userId).stream()
                .map(w -> w.getCourse().getId())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void toggleWishlist(UUID userId, UUID courseId) {
        if (wishlistRepository.existsByUserIdAndCourseId(userId, courseId)) {
            wishlistRepository.deleteByUserIdAndCourseId(userId, courseId);
        } else {
            UserProfile user = userProfileRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));
            wishlistRepository.save(Wishlist.builder().user(user).course(course).build());
        }
    }

    private void generateCertificate(Enrollment enrollment) {
        if (enrollment.getCertificate() == null) {
            String code = "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            Certificate cert = Certificate.builder()
                    .enrollment(enrollment)
                    .certificateCode(code)
                    .issuedAt(Instant.now())
                    .build();
            certificateRepository.save(cert);
        }
    }

    private EnrollmentDto mapToDto(Enrollment enrollment) {
        Course course = enrollment.getCourse();
        CourseSummaryDto summary = CourseSummaryDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .slug(course.getSlug())
                .subtitle(course.getSubtitle())
                .categoryName(course.getCategory() != null ? course.getCategory().getName() : "General")
                .instructorName(course.getInstructor() != null ? course.getInstructor().getFullName() : "Instructor")
                .thumbnailUrl(course.getThumbnailUrl())
                .build();

        String certCode = enrollment.getCertificate() != null ? enrollment.getCertificate().getCertificateCode() : null;

        return EnrollmentDto.builder()
                .id(enrollment.getId())
                .course(summary)
                .progressPercentage(enrollment.getProgressPercentage())
                .status(enrollment.getStatus())
                .enrolledAt(enrollment.getEnrolledAt())
                .completedAt(enrollment.getCompletedAt())
                .certificateCode(certCode)
                .build();
    }

    private CertificateDto mapToCertificateDto(Certificate cert) {
        return CertificateDto.builder()
                .id(cert.getId())
                .certificateCode(cert.getCertificateCode())
                .studentName(cert.getEnrollment().getUser().getFullName())
                .courseTitle(cert.getEnrollment().getCourse().getTitle())
                .instructorName(cert.getEnrollment().getCourse().getInstructor().getFullName())
                .issuedAt(cert.getIssuedAt())
                .pdfUrl(cert.getPdfUrl())
                .build();
    }
}
