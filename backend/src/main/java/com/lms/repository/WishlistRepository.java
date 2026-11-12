package com.lms.repository;

import com.lms.entity.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, UUID> {
    Page<Wishlist> findByStudentId(UUID studentId, Pageable pageable);
    Optional<Wishlist> findByStudentIdAndCourseId(UUID studentId, UUID courseId);
    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);
    void deleteByStudentIdAndCourseId(UUID studentId, UUID courseId);
}

