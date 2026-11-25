package com.lms.backend.repository;

import com.lms.backend.entity.Course;
import com.lms.backend.enums.CourseLevel;
import com.lms.backend.enums.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    Optional<Course> findBySlug(String slug);
    Page<Course> findByStatus(CourseStatus status, Pageable pageable);
    List<Course> findByInstructorId(UUID instructorId);
    long countByStatus(CourseStatus status);

    @Query("SELECT c FROM Course c WHERE c.status = 'APPROVED' " +
           "AND (:categorySlug IS NULL OR c.category.slug = :categorySlug) " +
           "AND (:level IS NULL OR c.level = :level) " +
           "AND (:query IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "     OR LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Course> searchCourses(
            @Param("categorySlug") String categorySlug,
            @Param("level") CourseLevel level,
            @Param("query") String query,
            Pageable pageable
    );
}
