package com.lms.service;

import com.lms.dto.common.PageResponse;
import com.lms.dto.course.CourseDto;
import com.lms.dto.course.LessonDto;
import com.lms.dto.course.ModuleDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    // Public catalogue
    PageResponse<CourseDto.Response> getPublishedCourses(String keyword, UUID categoryId, String level, Pageable pageable);
    CourseDto.Response getCourseBySlug(String slug);
    CourseDto.Response getCourseById(UUID id);

    // Instructor course management
    CourseDto.Response createCourse(UUID instructorId, CourseDto.CreateRequest request);
    CourseDto.Response updateCourse(UUID courseId, UUID instructorId, CourseDto.UpdateRequest request);
    void deleteCourse(UUID courseId, UUID instructorId);
    CourseDto.Response submitForApproval(UUID courseId, UUID instructorId);
    PageResponse<CourseDto.Response> getInstructorCourses(UUID instructorId, Pageable pageable);

    // Module management
    ModuleDto.Response addModule(UUID courseId, UUID instructorId, ModuleDto.Request request);
    ModuleDto.Response updateModule(UUID moduleId, UUID instructorId, ModuleDto.Request request);
    void deleteModule(UUID moduleId, UUID instructorId);
    List<ModuleDto.Response> getCourseModules(UUID courseId);

    // Lesson management
    LessonDto.Response addLesson(UUID moduleId, UUID instructorId, LessonDto.Request request);
    LessonDto.Response updateLesson(UUID lessonId, UUID instructorId, LessonDto.Request request);
    void deleteLesson(UUID lessonId, UUID instructorId);
}

