package com.lms.service.impl;

import com.lms.dto.category.CategoryDto;
import com.lms.entity.Category;
import com.lms.exception.BadRequestException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.CategoryRepository;
import com.lms.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto.Response> getActiveCategories() {
        return categoryRepository.findByIsActiveTrueOrderByCreatedAtDesc().stream()
                .map(CategoryDto.Response::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto.Response> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto.Response::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto.Response getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
        return CategoryDto.Response.fromEntity(category);
    }

    @Override
    @Transactional
    public CategoryDto.Response createCategory(CategoryDto.Request request) {
        String name = request.getName().trim();
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new BadRequestException("Category with name '" + name + "' already exists");
        }

        String slug = (request.getSlug() != null && !request.getSlug().isBlank())
                ? toSlug(request.getSlug())
                : toSlug(name);

        if (categoryRepository.existsBySlugIgnoreCase(slug)) {
            slug = slug + "-" + System.currentTimeMillis() % 10000;
        }

        Category category = Category.builder()
                .name(name)
                .slug(slug)
                .description(request.getDescription())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();

        Category saved = categoryRepository.save(category);
        log.info("Created new category: {} ({})", saved.getName(), saved.getSlug());
        return CategoryDto.Response.fromEntity(saved);
    }

    @Override
    @Transactional
    public CategoryDto.Response updateCategory(UUID id, CategoryDto.Request request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        String newName = request.getName().trim();
        if (!category.getName().equalsIgnoreCase(newName) && categoryRepository.existsByNameIgnoreCase(newName)) {
            throw new BadRequestException("Category with name '" + newName + "' already exists");
        }

        category.setName(newName);
        if (request.getSlug() != null && !request.getSlug().isBlank()) {
            category.setSlug(toSlug(request.getSlug()));
        }
        category.setDescription(request.getDescription());
        if (request.getIsActive() != null) {
            category.setIsActive(request.getIsActive());
        }

        Category updated = categoryRepository.save(category);
        return CategoryDto.Response.fromEntity(updated);
    }

    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
        log.info("Deleted category ID: {}", id);
    }

    private String toSlug(String input) {
        String noWhiteSpace = WHITESPACE.matcher(input.trim().toLowerCase(Locale.ENGLISH)).replaceAll("-");
        String normalized = Normalizer.normalize(noWhiteSpace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.replaceAll("-+", "-");
    }
}

