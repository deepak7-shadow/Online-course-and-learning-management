package com.lms.repository;

import com.lms.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findBySlug(String slug);

    boolean existsByNameIgnoreCase(String name);

    boolean existsBySlugIgnoreCase(String slug);

    List<Category> findByIsActiveTrueOrderByCreatedAtDesc();
}

