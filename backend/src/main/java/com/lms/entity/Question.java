package com.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "questions")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "question_type", length = 50)
    @Builder.Default
    private String questionType = "SINGLE_CHOICE";

    // JSON stored as text: ["Option A","Option B","Option C","Option D"]
    @Column(name = "options", columnDefinition = "TEXT")
    private String options;

    // JSON stored as text: ["Option A"]  or ["Option A","Option B"] for multi
    @Column(name = "correct_answers", columnDefinition = "TEXT")
    private String correctAnswers;

    @Column(name = "explanation", columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "points")
    @Builder.Default
    private Integer points = 1;

    @Column(name = "order_index")
    @Builder.Default
    private Integer orderIndex = 0;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}

