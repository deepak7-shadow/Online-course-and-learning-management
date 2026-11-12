package com.lms.repository;

import com.lms.entity.Course;
import com.lms.enums.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    Optional<Course> findBySlug(String slug);

    Optional<Course> findBySlugAndStatus(String slug, CourseStatus status);

    boolean existsBySlug(String slug);

    Page<Course> findByStatus(CourseStatus status, Pageable pageable);

    long countByStatus(CourseStatus status);

    Page<Course> findByInstructorId(UUID instructorId, Pageable pageable);

    @Query("""
        SELECT c FROM Course c
        WHERE c.status = 'PUBLISHED'
        AND (:keyword IS NULL OR :keyword = ''
             OR LOWER(c.title) LIKE LOWER(CONCAT('%',:keyword,'%'))
             OR LOWER(c.description) LIKE LOWER(CONCAT('%',:keyword,'%')))
        AND (:categoryId IS NULL OR c.category.id = :categoryId)
        AND (:level IS NULL OR :level = '' OR LOWER(c.level) = LOWER(:level))
        """)
    Page<Course> searchPublished(@Param("keyword") String keyword,
                                 @Param("categoryId") UUID categoryId,
                                 @Param("level") String level,
                                 Pageable pageable);
}
