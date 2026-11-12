package com.lms.repository;

import com.lms.entity.CourseApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseApprovalRepository extends JpaRepository<CourseApproval, UUID> {

    List<CourseApproval> findByCourseIdOrderByDecidedAtDesc(UUID courseId);
}

