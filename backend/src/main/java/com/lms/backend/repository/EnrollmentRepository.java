package com.lms.backend.repository;

import com.lms.backend.entity.Enrollment;
import com.lms.backend.enums.EnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    Optional<Enrollment> findByUserIdAndCourseId(UUID userId, UUID courseId);
    Page<Enrollment> findByUserId(UUID userId, Pageable pageable);
    List<Enrollment> findByCourseId(UUID courseId);
    long countByStatus(EnrollmentStatus status);
    long countByCourseId(UUID courseId);
}
