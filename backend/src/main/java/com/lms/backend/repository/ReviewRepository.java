package com.lms.backend.repository;

import com.lms.backend.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Page<Review> findByCourseId(UUID courseId, Pageable pageable);
    Optional<Review> findByUserIdAndCourseId(UUID userId, UUID courseId);
}
