package com.lms.service.impl;

import com.lms.dto.common.PageResponse;
import com.lms.dto.course.CourseDto;
import com.lms.dto.course.LessonDto;
import com.lms.dto.course.ModuleDto;
import com.lms.entity.*;
import com.lms.enums.CourseStatus;
import com.lms.exception.BadRequestException;
import com.lms.exception.ForbiddenException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.*;
import com.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UserProfileRepository userProfileRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    private static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^a-z0-9\\s-]");
    private static final Pattern SPACES = Pattern.compile("[\\s]+");

    // ─── Public Catalogue ────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CourseDto.Response> getPublishedCourses(String keyword, UUID categoryId, String level, Pageable pageable) {
        Page<Course> page = courseRepository.searchPublished(keyword, categoryId, level, pageable);
        return PageResponse.from(page.map(CourseDto.Response::fromEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto.Response getCourseBySlug(String slug) {
        Course course = courseRepository.findBySlugAndStatus(slug, CourseStatus.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + slug));
        return CourseDto.Response.fromEntity(course);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto.Response getCourseById(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + id));
        return CourseDto.Response.fromEntity(course);
    }

    // ─── Instructor Course Management ────────────────────────────────────────

    @Override
    @Transactional
    public CourseDto.Response createCourse(UUID instructorId, CourseDto.CreateRequest request) {
        UserProfile instructor = userProfileRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found: " + instructorId));

        String slug = generateUniqueSlug(request.getTitle());

        Course course = Course.builder()
                .title(request.getTitle())
                .slug(slug)
                .subtitle(request.getSubtitle())
                .description(request.getDescription())
                .thumbnailUrl(request.getThumbnailUrl())
                .price(request.getPrice())
                .instructor(instructor)
                .status(CourseStatus.DRAFT)
                .level(request.getLevel() != null ? request.getLevel() : "BEGINNER")
                .language(request.getLanguage() != null ? request.getLanguage() : "English")
                .build();

        if (request.getCategoryId() != null) {
            categoryRepository.findById(request.getCategoryId())
                    .ifPresent(course::setCategory);
        }

        Course saved = courseRepository.save(course);
        log.info("Instructor {} created course: {}", instructorId, saved.getId());
        return CourseDto.Response.fromEntity(saved);
    }

    @Override
    @Transactional
    public CourseDto.Response updateCourse(UUID courseId, UUID instructorId, CourseDto.UpdateRequest request) {
        Course course = findCourseOwnedBy(courseId, instructorId);

        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            course.setTitle(request.getTitle().trim());
        }
        if (request.getSubtitle() != null) course.setSubtitle(request.getSubtitle());
        if (request.getDescription() != null) course.setDescription(request.getDescription());
        if (request.getThumbnailUrl() != null) course.setThumbnailUrl(request.getThumbnailUrl());
        if (request.getPrice() != null) course.setPrice(request.getPrice());
        if (request.getLevel() != null) course.setLevel(request.getLevel());
        if (request.getLanguage() != null) course.setLanguage(request.getLanguage());
        if (request.getCategoryId() != null) {
            categoryRepository.findById(request.getCategoryId()).ifPresent(course::setCategory);
        }

        return CourseDto.Response.fromEntity(courseRepository.save(course));
    }

    @Override
    @Transactional
    public void deleteCourse(UUID courseId, UUID instructorId) {
        Course course = findCourseOwnedBy(courseId, instructorId);
        if (course.getStatus() == CourseStatus.PUBLISHED) {
            throw new BadRequestException("Cannot delete a published course. Unpublish it first.");
        }
        courseRepository.delete(course);
        log.info("Instructor {} deleted course {}", instructorId, courseId);
    }

    @Override
    @Transactional
    public CourseDto.Response submitForApproval(UUID courseId, UUID instructorId) {
        Course course = findCourseOwnedBy(courseId, instructorId);
        if (course.getStatus() != CourseStatus.DRAFT && course.getStatus() != CourseStatus.REJECTED) {
            throw new BadRequestException("Only DRAFT or REJECTED courses can be submitted for review.");
        }
        course.setStatus(CourseStatus.PENDING_APPROVAL);
        return CourseDto.Response.fromEntity(courseRepository.save(course));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CourseDto.Response> getInstructorCourses(UUID instructorId, Pageable pageable) {
        Page<Course> page = courseRepository.findByInstructorId(instructorId, pageable);
        return PageResponse.from(page.map(CourseDto.Response::fromEntity));
    }

    // ─── Module Management ───────────────────────────────────────────────────

    @Override
    @Transactional
    public ModuleDto.Response addModule(UUID courseId, UUID instructorId, ModuleDto.Request request) {
        Course course = findCourseOwnedBy(courseId, instructorId);
        int nextOrder = (int) moduleRepository.countByCourseId(courseId);

        Module module = Module.builder()
                .course(course)
                .title(request.getTitle())
                .description(request.getDescription())
                .orderIndex(request.getOrderIndex() != null ? request.getOrderIndex() : nextOrder)
                .build();

        return ModuleDto.Response.fromEntity(moduleRepository.save(module));
    }

    @Override
    @Transactional
    public ModuleDto.Response updateModule(UUID moduleId, UUID instructorId, ModuleDto.Request request) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found: " + moduleId));
        verifyModuleOwnership(module, instructorId);

        if (request.getTitle() != null) module.setTitle(request.getTitle());
        if (request.getDescription() != null) module.setDescription(request.getDescription());
        if (request.getOrderIndex() != null) module.setOrderIndex(request.getOrderIndex());

        return ModuleDto.Response.fromEntity(moduleRepository.save(module));
    }

    @Override
    @Transactional
    public void deleteModule(UUID moduleId, UUID instructorId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found: " + moduleId));
        verifyModuleOwnership(module, instructorId);
        moduleRepository.delete(module);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuleDto.Response> getCourseModules(UUID courseId) {
        return moduleRepository.findByCourseIdOrderByOrderIndexAsc(courseId).stream()
                .map(m -> {
                    ModuleDto.Response dto = ModuleDto.Response.fromEntity(m);
                    dto.setLessons(
                        lessonRepository.findByModuleIdOrderByOrderIndexAsc(m.getId()).stream()
                            .map(LessonDto.Summary::fromEntity)
                            .collect(Collectors.toList())
                    );
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // ─── Lesson Management ───────────────────────────────────────────────────

    @Override
    @Transactional
    public LessonDto.Response addLesson(UUID moduleId, UUID instructorId, LessonDto.Request request) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found: " + moduleId));
        verifyModuleOwnership(module, instructorId);

        int nextOrder = (int) lessonRepository.countByModuleId(moduleId);
        Lesson lesson = Lesson.builder()
                .module(module)
                .title(request.getTitle())
                .content(request.getContent())
                .videoUrl(request.getVideoUrl())
                .durationMinutes(request.getDurationMinutes() != null ? request.getDurationMinutes() : 0)
                .isFreePreview(request.getIsFreePreview() != null ? request.getIsFreePreview() : false)
                .orderIndex(request.getOrderIndex() != null ? request.getOrderIndex() : nextOrder)
                .type(request.getType() != null ? request.getType() : "VIDEO")
                .build();

        return LessonDto.Response.fromEntity(lessonRepository.save(lesson));
    }

    @Override
    @Transactional
    public LessonDto.Response updateLesson(UUID lessonId, UUID instructorId, LessonDto.Request request) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found: " + lessonId));
        verifyModuleOwnership(lesson.getModule(), instructorId);

        if (request.getTitle() != null) lesson.setTitle(request.getTitle());
        if (request.getContent() != null) lesson.setContent(request.getContent());
        if (request.getVideoUrl() != null) lesson.setVideoUrl(request.getVideoUrl());
        if (request.getDurationMinutes() != null) lesson.setDurationMinutes(request.getDurationMinutes());
        if (request.getIsFreePreview() != null) lesson.setIsFreePreview(request.getIsFreePreview());
        if (request.getOrderIndex() != null) lesson.setOrderIndex(request.getOrderIndex());
        if (request.getType() != null) lesson.setType(request.getType());

        return LessonDto.Response.fromEntity(lessonRepository.save(lesson));
    }

    @Override
    @Transactional
    public void deleteLesson(UUID lessonId, UUID instructorId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found: " + lessonId));
        verifyModuleOwnership(lesson.getModule(), instructorId);
        lessonRepository.delete(lesson);
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private Course findCourseOwnedBy(UUID courseId, UUID instructorId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not own this course.");
        }
        return course;
    }

    private void verifyModuleOwnership(Module module, UUID instructorId) {
        if (!module.getCourse().getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not own this course module.");
        }
    }

    private String generateUniqueSlug(String title) {
        String base = NON_ALPHANUMERIC.matcher(
                SPACES.matcher(
                    Normalizer.normalize(title.trim().toLowerCase(Locale.ENGLISH), Normalizer.Form.NFD)
                ).replaceAll("-")
        ).replaceAll("").replaceAll("-+", "-");

        String slug = base;
        int suffix = 1;
        while (courseRepository.existsBySlug(slug)) {
            slug = base + "-" + suffix++;
        }
        return slug;
    }
}

