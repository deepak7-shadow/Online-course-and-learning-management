package com.lms.repository;

import com.lms.entity.Course;
import com.lms.enums.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    Optional<Course> findBySlug(String slug);

    Page<Course> findByStatus(CourseStatus status, Pageable pageable);

    long countByStatus(CourseStatus status);

    Page<Course> findByInstructorId(UUID instructorId, Pageable pageable);
}
