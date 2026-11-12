package com.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "enrollments",
    uniqueConstraints = @UniqueConstraint(columnNames = {"student_id","course_id"}))
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private UserProfile student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @CreatedDate
    @Column(name = "enrolled_at", updatable = false)
    private OffsetDateTime enrolledAt;

    @Column(name = "status", length = 50)
    @Builder.Default
    private String status = "ACTIVE";

    @Column(name = "progress_percentage", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal progressPercentage = BigDecimal.ZERO;

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;
}

