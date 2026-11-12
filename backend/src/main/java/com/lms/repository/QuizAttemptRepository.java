package com.lms.repository;

import com.lms.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, UUID> {
    List<QuizAttempt> findByStudentIdAndQuizIdOrderByAttemptedAtDesc(UUID studentId, UUID quizId);
    List<QuizAttempt> findByStudentIdOrderByAttemptedAtDesc(UUID studentId);
}

