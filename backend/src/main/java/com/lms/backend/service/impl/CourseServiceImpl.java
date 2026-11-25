package com.lms.backend.service.impl;

import com.lms.backend.dto.course.CourseDetailDto;
import com.lms.backend.dto.course.CourseSummaryDto;
import com.lms.backend.dto.course.CreateCourseRequest;
import com.lms.backend.dto.course.UpdateCourseRequest;
import com.lms.backend.dto.lesson.CreateLessonRequest;
import com.lms.backend.dto.lesson.LessonDto;
import com.lms.backend.dto.module.CreateModuleRequest;
import com.lms.backend.dto.module.ModuleDto;
import com.lms.backend.dto.quiz.QuizDto;
import com.lms.backend.entity.*;
import com.lms.backend.enums.CourseLevel;
import com.lms.backend.enums.CourseStatus;
import com.lms.backend.exception.ForbiddenException;
import com.lms.backend.exception.ResourceNotFoundException;
import com.lms.backend.repository.*;
import com.lms.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UserProfileRepository userProfileRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final QuizRepository quizRepository;
    private final EnrollmentRepository enrollmentRepository;

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    @Override
    @Transactional(readOnly = true)
    public Page<CourseSummaryDto> searchCourses(String categorySlug, CourseLevel level, String query, Pageable pageable) {
        return courseRepository.searchCourses(categorySlug, level, query, pageable)
                .map(this::mapToSummary);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDetailDto getCourseDetail(String idOrSlug) {
        Course course;
        try {
            UUID id = UUID.fromString(idOrSlug);
            course = courseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + idOrSlug));
        } catch (IllegalArgumentException e) {
            course = courseRepository.findBySlug(idOrSlug)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + idOrSlug));
        }
        return mapToDetail(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseSummaryDto> getInstructorCourses(UUID instructorId) {
        return courseRepository.findByInstructorId(instructorId).stream()
                .map(this::mapToSummary)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseDetailDto createCourse(UUID instructorId, CreateCourseRequest request) {
        UserProfile instructor = userProfileRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found: " + instructorId));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + request.getCategoryId()));

        String slug = toSlug(request.getTitle()) + "-" + UUID.randomUUID().toString().substring(0, 8);

        Course course = Course.builder()
                .title(request.getTitle())
                .slug(slug)
                .subtitle(request.getSubtitle())
                .description(request.getDescription())
                .category(category)
                .instructor(instructor)
                .price(request.getPrice())
                .level(request.getLevel())
                .status(CourseStatus.DRAFT)
                .thumbnailUrl(request.getThumbnailUrl())
                .promoVideoUrl(request.getPromoVideoUrl())
                .build();

        return mapToDetail(courseRepository.save(course));
    }

    @Override
    @Transactional
    public CourseDetailDto updateCourse(UUID courseId, UUID instructorId, UpdateCourseRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You are not authorized to update this course");
        }

        if (request.getTitle() != null) course.setTitle(request.getTitle());
        if (request.getSubtitle() != null) course.setSubtitle(request.getSubtitle());
        if (request.getDescription() != null) course.setDescription(request.getDescription());
        if (request.getPrice() != null) course.setPrice(request.getPrice());
        if (request.getLevel() != null) course.setLevel(request.getLevel());
        if (request.getThumbnailUrl() != null) course.setThumbnailUrl(request.getThumbnailUrl());
        if (request.getPromoVideoUrl() != null) course.setPromoVideoUrl(request.getPromoVideoUrl());
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + request.getCategoryId()));
            course.setCategory(category);
        }

        return mapToDetail(courseRepository.save(course));
    }

    @Override
    @Transactional
    public void submitCourseForReview(UUID courseId, UUID instructorId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not own this course");
        }
        course.setStatus(CourseStatus.SUBMITTED);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public ModuleDto addModule(UUID courseId, UUID instructorId, CreateModuleRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not own this course");
        }

        int order = request.getOrderIndex() != null ? request.getOrderIndex() : course.getModules().size() + 1;
        Module module = Module.builder()
                .course(course)
                .title(request.getTitle())
                .description(request.getDescription())
                .orderIndex(order)
                .build();
        module = moduleRepository.save(module);

        return ModuleDto.builder()
                .id(module.getId())
                .courseId(course.getId())
                .title(module.getTitle())
                .description(module.getDescription())
                .orderIndex(module.getOrderIndex())
                .lessons(new ArrayList<>())
                .build();
    }

    @Override
    @Transactional
    public LessonDto addLesson(UUID moduleId, UUID instructorId, CreateLessonRequest request) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found: " + moduleId));
        if (!module.getCourse().getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not own this course");
        }

        int order = request.getOrderIndex() != null ? request.getOrderIndex() : module.getLessons().size() + 1;
        Lesson lesson = Lesson.builder()
                .module(module)
                .title(request.getTitle())
                .contentType(request.getContentType())
                .videoUrl(request.getVideoUrl())
                .contentText(request.getContentText())
                .durationMinutes(request.getDurationMinutes() != null ? request.getDurationMinutes() : 0)
                .orderIndex(order)
                .isFreePreview(Boolean.TRUE.equals(request.getIsFreePreview()))
                .build();
        lesson = lessonRepository.save(lesson);

        return LessonDto.builder()
                .id(lesson.getId())
                .moduleId(module.getId())
                .title(lesson.getTitle())
                .contentType(lesson.getContentType())
                .videoUrl(lesson.getVideoUrl())
                .contentText(lesson.getContentText())
                .durationMinutes(lesson.getDurationMinutes())
                .orderIndex(lesson.getOrderIndex())
                .isFreePreview(lesson.getIsFreePreview())
                .isCompleted(false)
                .build();
    }

    @Override
    @Transactional
    public QuizDto createOrUpdateQuiz(UUID lessonId, UUID instructorId, QuizDto quizDto) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found: " + lessonId));
        if (!lesson.getModule().getCourse().getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not own this course");
        }

        Quiz quiz = quizRepository.findByLessonId(lessonId)
                .orElseGet(() -> Quiz.builder().lesson(lesson).build());

        quiz.setTitle(quizDto.getTitle());
        quiz.setDescription(quizDto.getDescription());
        quiz.setPassingPercentage(quizDto.getPassingPercentage() != null ? quizDto.getPassingPercentage() : 70);

        quiz = quizRepository.save(quiz);
        quizDto.setId(quiz.getId());
        quizDto.setLessonId(lessonId);
        return quizDto;
    }

    private CourseSummaryDto mapToSummary(Course course) {
        int totalLessons = course.getModules().stream().mapToInt(m -> m.getLessons().size()).sum();
        int totalMinutes = course.getModules().stream()
                .flatMap(m -> m.getLessons().stream())
                .mapToInt(Lesson::getDurationMinutes)
                .sum();
        long enrollments = enrollmentRepository.countByCourseId(course.getId());

        return CourseSummaryDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .slug(course.getSlug())
                .subtitle(course.getSubtitle())
                .categoryName(course.getCategory() != null ? course.getCategory().getName() : "General")
                .instructorName(course.getInstructor() != null ? course.getInstructor().getFullName() : "Instructor")
                .price(course.getPrice())
                .level(course.getLevel())
                .status(course.getStatus())
                .thumbnailUrl(course.getThumbnailUrl())
                .totalLessons(totalLessons)
                .totalDurationMinutes(totalMinutes)
                .averageRating(4.8)
                .enrollmentCount(enrollments)
                .build();
    }

    private CourseDetailDto mapToDetail(Course course) {
        List<ModuleDto> moduleDtos = course.getModules().stream()
                .map(m -> ModuleDto.builder()
                        .id(m.getId())
                        .courseId(course.getId())
                        .title(m.getTitle())
                        .description(m.getDescription())
                        .orderIndex(m.getOrderIndex())
                        .lessons(m.getLessons().stream().map(l -> LessonDto.builder()
                                .id(l.getId())
                                .moduleId(m.getId())
                                .title(l.getTitle())
                                .contentType(l.getContentType())
                                .videoUrl(l.getVideoUrl())
                                .contentText(l.getContentText())
                                .durationMinutes(l.getDurationMinutes())
                                .orderIndex(l.getOrderIndex())
                                .isFreePreview(l.getIsFreePreview())
                                .isCompleted(false)
                                .build()).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        return CourseDetailDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .slug(course.getSlug())
                .subtitle(course.getSubtitle())
                .description(course.getDescription())
                .categoryId(course.getCategory() != null ? course.getCategory().getId() : null)
                .categoryName(course.getCategory() != null ? course.getCategory().getName() : null)
                .instructorId(course.getInstructor().getId())
                .instructorName(course.getInstructor().getFullName())
                .instructorBio(course.getInstructor().getBio())
                .price(course.getPrice())
                .level(course.getLevel())
                .status(course.getStatus())
                .thumbnailUrl(course.getThumbnailUrl())
                .promoVideoUrl(course.getPromoVideoUrl())
                .modules(moduleDtos)
                .createdAt(course.getCreatedAt())
                .build();
    }

    private String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
