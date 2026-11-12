package com.lms.service.impl;

import com.lms.dto.common.PageResponse;
import com.lms.dto.learning.*;
import com.lms.entity.*;
import com.lms.exception.BadRequestException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.*;
import com.lms.service.LearningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LearningServiceImpl implements LearningService {

    private final EnrollmentRepository enrollmentRepository;
    private final LessonProgressRepository lessonProgressRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final CertificateRepository certificateRepository;
    private final ReviewRepository reviewRepository;
    private final WishlistRepository wishlistRepository;
    private final UserProfileRepository userProfileRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    // ─── Enrollment ──────────────────────────────────────────────────────────

    @Override
    @Transactional
    public EnrollmentDto.Response enrollStudent(UUID studentId, UUID courseId) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new BadRequestException("You are already enrolled in this course.");
        }
        UserProfile student = userProfileRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .status("ACTIVE")
                .progressPercentage(BigDecimal.ZERO)
                .build();

        Enrollment saved = enrollmentRepository.save(enrollment);
        log.info("Student {} enrolled in course {}", studentId, courseId);
        return EnrollmentDto.Response.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<EnrollmentDto.Response> getStudentEnrollments(UUID studentId, Pageable pageable) {
        Page<Enrollment> page = enrollmentRepository.findByStudentId(studentId, pageable);
        return PageResponse.from(page.map(EnrollmentDto.Response::fromEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public EnrollmentDto.ProgressResponse getCourseProgress(UUID studentId, UUID courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found for course: " + courseId));

        // Count total lessons across all modules
        List<Module> modules = moduleRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
        long totalLessons = modules.stream()
                .mapToLong(m -> lessonRepository.countByModuleId(m.getId()))
                .sum();

        long completed = lessonProgressRepository.countCompletedLessons(enrollment.getId());
        BigDecimal pct = totalLessons > 0
                ? BigDecimal.valueOf((double) completed / totalLessons * 100).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return EnrollmentDto.ProgressResponse.builder()
                .enrollmentId(enrollment.getId())
                .courseId(courseId)
                .totalLessons(totalLessons)
                .completedLessons(completed)
                .progressPercentage(pct)
                .isCompleted(pct.compareTo(BigDecimal.valueOf(100)) >= 0)
                .build();
    }

    // ─── Lesson Progress ─────────────────────────────────────────────────────

    @Override
    @Transactional
    public void markLessonComplete(UUID studentId, UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found: " + lessonId));
        UUID courseId = lesson.getModule().getCourse().getId();

        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new BadRequestException("You must be enrolled to track lesson progress."));

        LessonProgress progress = lessonProgressRepository
                .findByEnrollmentIdAndLessonId(enrollment.getId(), lessonId)
                .orElse(LessonProgress.builder()
                        .enrollment(enrollment)
                        .lesson(lesson)
                        .build());

        if (!Boolean.TRUE.equals(progress.getIsCompleted())) {
            progress.setIsCompleted(true);
            progress.setCompletedAt(OffsetDateTime.now());
            lessonProgressRepository.save(progress);
            recalculateProgress(enrollment);
        }
    }

    private void recalculateProgress(Enrollment enrollment) {
        List<Module> modules = moduleRepository.findByCourseIdOrderByOrderIndexAsc(enrollment.getCourse().getId());
        long total = modules.stream().mapToLong(m -> lessonRepository.countByModuleId(m.getId())).sum();
        long done = lessonProgressRepository.countCompletedLessons(enrollment.getId());
        BigDecimal pct = total > 0
                ? BigDecimal.valueOf((double) done / total * 100).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
        enrollment.setProgressPercentage(pct);
        if (pct.compareTo(BigDecimal.valueOf(100)) >= 0 && enrollment.getCompletedAt() == null) {
            enrollment.setCompletedAt(OffsetDateTime.now());
            enrollment.setStatus("COMPLETED");
        }
        enrollmentRepository.save(enrollment);
    }

    // ─── Quiz ─────────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public QuizDto.Response getQuiz(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found: " + quizId));
        int count = questionRepository.findByQuizIdOrderByOrderIndexAsc(quizId).size();
        return QuizDto.Response.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .passingScore(quiz.getPassingScore())
                .timeLimitMinutes(quiz.getTimeLimitMinutes())
                .totalQuestions(count)
                .createdAt(quiz.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public QuizDto.AttemptResult submitQuiz(UUID studentId, UUID quizId, QuizDto.SubmitRequest request) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found: " + quizId));
        List<Question> questions = questionRepository.findByQuizIdOrderByOrderIndexAsc(quizId);
        UserProfile student = userProfileRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));

        if (questions.isEmpty()) {
            throw new BadRequestException("Quiz has no questions.");
        }

        // Grade answers
        int totalPoints = questions.stream().mapToInt(Question::getPoints).sum();
        int earnedPoints = 0;
        for (Question q : questions) {
            String submitted = request.getAnswers() != null
                    ? request.getAnswers().get(q.getId().toString()) : null;
            if (submitted != null && q.getCorrectAnswers() != null
                    && q.getCorrectAnswers().contains(submitted)) {
                earnedPoints += q.getPoints();
            }
        }

        BigDecimal score = totalPoints > 0
                ? BigDecimal.valueOf((double) earnedPoints / totalPoints * 100).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
        boolean passed = score.intValue() >= quiz.getPassingScore();

        QuizAttempt attempt = QuizAttempt.builder()
                .quiz(quiz)
                .student(student)
                .score(score)
                .isPassed(passed)
                .submittedAnswers(request.getAnswers() != null ? request.getAnswers().toString() : "{}")
                .build();

        QuizAttempt saved = quizAttemptRepository.save(attempt);

        return QuizDto.AttemptResult.builder()
                .attemptId(saved.getId())
                .quizId(quizId)
                .score(score)
                .isPassed(passed)
                .passingScore(quiz.getPassingScore())
                .attemptedAt(saved.getAttemptedAt())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizDto.AttemptResult> getMyAttempts(UUID studentId, UUID quizId) {
        return quizAttemptRepository
                .findByStudentIdAndQuizIdOrderByAttemptedAtDesc(studentId, quizId)
                .stream()
                .map(a -> QuizDto.AttemptResult.builder()
                        .attemptId(a.getId())
                        .quizId(quizId)
                        .score(a.getScore())
                        .isPassed(a.getIsPassed())
                        .attemptedAt(a.getAttemptedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // ─── Certificate ─────────────────────────────────────────────────────────

    @Override
    @Transactional
    public CertificateDto issueCertificate(UUID studentId, UUID courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new BadRequestException("Not enrolled in this course."));

        if (!"COMPLETED".equals(enrollment.getStatus())) {
            throw new BadRequestException("Course must be fully completed before a certificate can be issued.");
        }
        if (certificateRepository.existsByEnrollmentId(enrollment.getId())) {
            return certificateRepository.findByEnrollmentId(enrollment.getId())
                    .map(this::mapCertificate).orElseThrow();
        }

        String code = "LMS-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        Certificate cert = Certificate.builder()
                .enrollment(enrollment)
                .student(enrollment.getStudent())
                .course(enrollment.getCourse())
                .certificateCode(code)
                .build();

        return mapCertificate(certificateRepository.save(cert));
    }

    @Override
    @Transactional(readOnly = true)
    public CertificateDto getCertificateByCode(String code) {
        Certificate cert = certificateRepository.findByCertificateCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found: " + code));
        return mapCertificate(cert);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDto> getStudentCertificates(UUID studentId) {
        return certificateRepository.findAll().stream()
                .filter(c -> c.getStudent().getId().equals(studentId))
                .map(this::mapCertificate)
                .collect(Collectors.toList());
    }

    private CertificateDto mapCertificate(Certificate c) {
        return CertificateDto.builder()
                .id(c.getId())
                .enrollmentId(c.getEnrollment().getId())
                .studentId(c.getStudent().getId())
                .courseId(c.getCourse().getId())
                .courseTitle(c.getCourse().getTitle())
                .studentName(c.getStudent().getFullName())
                .certificateCode(c.getCertificateCode())
                .issuedAt(c.getIssuedAt())
                .pdfUrl(c.getPdfUrl())
                .build();
    }

    // ─── Reviews ─────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public ReviewDto.Response addOrUpdateReview(UUID studentId, UUID courseId, ReviewDto.Request request) {
        if (request.getRating() == null || request.getRating() < 1 || request.getRating() > 5) {
            throw new BadRequestException("Rating must be between 1 and 5.");
        }
        UserProfile student = userProfileRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        Review review = reviewRepository.findByCourseIdAndStudentId(courseId, studentId)
                .orElse(Review.builder().course(course).student(student).build());

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        Review saved = reviewRepository.save(review);

        return mapReview(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ReviewDto.Response> getCourseReviews(UUID courseId, Pageable pageable) {
        Page<Review> page = reviewRepository.findByCourseId(courseId, pageable);
        return PageResponse.from(page.map(this::mapReview));
    }

    private ReviewDto.Response mapReview(Review r) {
        return ReviewDto.Response.builder()
                .id(r.getId())
                .courseId(r.getCourse().getId())
                .studentId(r.getStudent().getId())
                .studentName(r.getStudent().getFullName())
                .rating(r.getRating())
                .comment(r.getComment())
                .createdAt(r.getCreatedAt())
                .build();
    }

    // ─── Wishlist ─────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public void addToWishlist(UUID studentId, UUID courseId) {
        if (wishlistRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new BadRequestException("Course is already in your wishlist.");
        }
        UserProfile student = userProfileRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        wishlistRepository.save(Wishlist.builder().student(student).course(course).build());
    }

    @Override
    @Transactional
    public void removeFromWishlist(UUID studentId, UUID courseId) {
        wishlistRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<EnrollmentDto.Response> getWishlist(UUID studentId, Pageable pageable) {
        Page<Wishlist> page = wishlistRepository.findByStudentId(studentId, pageable);
        // Reuse EnrollmentDto.Response shape for wishlist items (courseId, courseTitle, etc.)
        return PageResponse.from(page.map(w -> EnrollmentDto.Response.builder()
                .courseId(w.getCourse().getId())
                .courseTitle(w.getCourse().getTitle())
                .courseThumbnail(w.getCourse().getThumbnailUrl())
                .build()));
    }
}

