package com.lms.backend.service;

import com.lms.backend.dto.course.CourseDetailDto;
import com.lms.backend.dto.course.CourseSummaryDto;
import com.lms.backend.dto.course.CreateCourseRequest;
import com.lms.backend.dto.course.UpdateCourseRequest;
import com.lms.backend.dto.lesson.CreateLessonRequest;
import com.lms.backend.dto.lesson.LessonDto;
import com.lms.backend.dto.module.CreateModuleRequest;
import com.lms.backend.dto.module.ModuleDto;
import com.lms.backend.dto.quiz.QuizDto;
import com.lms.backend.enums.CourseLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    Page<CourseSummaryDto> searchCourses(String categorySlug, CourseLevel level, String query, Pageable pageable);
    CourseDetailDto getCourseDetail(String idOrSlug);
    List<CourseSummaryDto> getInstructorCourses(UUID instructorId);
    CourseDetailDto createCourse(UUID instructorId, CreateCourseRequest request);
    CourseDetailDto updateCourse(UUID courseId, UUID instructorId, UpdateCourseRequest request);
    void submitCourseForReview(UUID courseId, UUID instructorId);
    ModuleDto addModule(UUID courseId, UUID instructorId, CreateModuleRequest request);
    LessonDto addLesson(UUID moduleId, UUID instructorId, CreateLessonRequest request);
    QuizDto createOrUpdateQuiz(UUID lessonId, UUID instructorId, QuizDto quizDto);
}
