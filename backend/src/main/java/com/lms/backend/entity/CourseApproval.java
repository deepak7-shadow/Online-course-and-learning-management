package com.lms.backend.entity;

import com.lms.backend.enums.ApprovalDecision;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_approvals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseApproval extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by", nullable = false)
    private UserProfile reviewedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalDecision decision;

    @Column(columnDefinition = "TEXT")
    private String comments;
}
