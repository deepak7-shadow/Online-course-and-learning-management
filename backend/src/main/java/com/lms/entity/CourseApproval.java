package com.lms.entity;

import com.lms.enums.ApprovalDecision;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "course_approvals")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private UserProfile admin;

    @Enumerated(EnumType.STRING)
    @Column(name = "decision", nullable = false, length = 50)
    private ApprovalDecision decision;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @CreatedDate
    @Column(name = "decided_at", nullable = false, updatable = false)
    private OffsetDateTime decidedAt;
}
