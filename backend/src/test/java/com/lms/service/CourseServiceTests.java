package com.lms.service;

import com.lms.dto.course.CourseDto;
import com.lms.entity.Category;
import com.lms.entity.Course;
import com.lms.entity.UserProfile;
import com.lms.enums.CourseStatus;
import com.lms.enums.Role;
import com.lms.exception.ForbiddenException;
import com.lms.repository.*;
import com.lms.service.impl.CourseServiceImpl;
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
class CourseServiceTests {

    @Mock private CourseRepository courseRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private UserProfileRepository userProfileRepository;
    @Mock private ModuleRepository moduleRepository;
    @Mock private LessonRepository lessonRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private UUID instructorId;
    private UserProfile instructor;

    @BeforeEach
    void setup() {
        instructorId = UUID.randomUUID();
        instructor = UserProfile.builder()
                .id(instructorId)
                .email("instructor@test.com")
                .role(Role.ROLE_INSTRUCTOR)
                .build();
    }

    @Test
    @DisplayName("Instructor can create a draft course")
    void testCreateCourseSetsStatusToDraft() {
        when(userProfileRepository.findById(instructorId)).thenReturn(Optional.of(instructor));
        when(courseRepository.existsBySlug(any())).thenReturn(false);
        when(courseRepository.save(any(Course.class))).thenAnswer(inv -> {
            Course c = inv.getArgument(0);
            c.setId(UUID.randomUUID());
            return c;
        });

        CourseDto.CreateRequest req = CourseDto.CreateRequest.builder()
                .title("Spring Boot for Beginners")
                .build();

        CourseDto.Response result = courseService.createCourse(instructorId, req);

        assertNotNull(result);
        assertEquals(CourseStatus.DRAFT, result.getStatus());
        assertEquals(instructorId, result.getInstructorId());
    }

    @Test
    @DisplayName("Non-owner cannot update another instructor's course")
    void testNonOwnerCannotUpdateCourse() {
        UUID otherId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        Course course = Course.builder()
                .id(courseId)
                .title("Owned Course")
                .status(CourseStatus.DRAFT)
                .instructor(instructor)
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        assertThrows(ForbiddenException.class, () ->
            courseService.updateCourse(courseId, otherId, CourseDto.UpdateRequest.builder().title("Hack").build())
        );
    }

    @Test
    @DisplayName("Submitting a DRAFT course sets status to PENDING_APPROVAL")
    void testSubmitForApprovalChangesCourseStatus() {
        UUID courseId = UUID.randomUUID();
        Course course = Course.builder()
                .id(courseId)
                .title("Advanced Java")
                .status(CourseStatus.DRAFT)
                .instructor(instructor)
                .build();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenAnswer(inv -> inv.getArgument(0));

        CourseDto.Response result = courseService.submitForApproval(courseId, instructorId);

        assertEquals(CourseStatus.PENDING_APPROVAL, result.getStatus());
    }
}

