package com.lms.service;

import com.lms.dto.learning.EnrollmentDto;
import com.lms.entity.*;
import com.lms.enums.Role;
import com.lms.exception.BadRequestException;
import com.lms.repository.*;
import com.lms.service.impl.LearningServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LearningServiceTests {

    @Mock private EnrollmentRepository enrollmentRepository;
    @Mock private LessonProgressRepository lessonProgressRepository;
    @Mock private QuizRepository quizRepository;
    @Mock private QuestionRepository questionRepository;
    @Mock private QuizAttemptRepository quizAttemptRepository;
    @Mock private CertificateRepository certificateRepository;
    @Mock private ReviewRepository reviewRepository;
    @Mock private WishlistRepository wishlistRepository;
    @Mock private UserProfileRepository userProfileRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private LessonRepository lessonRepository;
    @Mock private ModuleRepository moduleRepository;

    @InjectMocks
    private LearningServiceImpl learningService;

    private UUID studentId;
    private UUID courseId;
    private UserProfile student;
    private Course course;

    @BeforeEach
    void setup() {
        studentId = UUID.randomUUID();
        courseId = UUID.randomUUID();

        student = UserProfile.builder()
                .id(studentId).email("student@test.com").role(Role.ROLE_STUDENT).build();

        UserProfile instructor = UserProfile.builder()
                .id(UUID.randomUUID()).email("teacher@test.com").role(Role.ROLE_INSTRUCTOR).build();

        course = Course.builder()
                .id(courseId).title("Test Course").instructor(instructor).build();
    }

    @Test
    @DisplayName("Enrolling a student creates an enrollment record")
    void testEnrollStudentSuccess() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)).thenReturn(false);
        when(userProfileRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(inv -> {
            Enrollment e = inv.getArgument(0);
            e.setId(UUID.randomUUID());
            return e;
        });

        EnrollmentDto.Response result = learningService.enrollStudent(studentId, courseId);

        assertNotNull(result);
        assertEquals(courseId, result.getCourseId());
        assertEquals(studentId, result.getStudentId());
    }

    @Test
    @DisplayName("Re-enrolling in same course throws BadRequestException")
    void testDuplicateEnrollmentThrows() {
        when(enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)).thenReturn(true);

        assertThrows(BadRequestException.class, () ->
            learningService.enrollStudent(studentId, courseId)
        );

        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Adding duplicate wishlist item throws BadRequestException")
    void testDuplicateWishlistThrows() {
        when(wishlistRepository.existsByStudentIdAndCourseId(studentId, courseId)).thenReturn(true);

        assertThrows(BadRequestException.class, () ->
            learningService.addToWishlist(studentId, courseId)
        );
    }
}

