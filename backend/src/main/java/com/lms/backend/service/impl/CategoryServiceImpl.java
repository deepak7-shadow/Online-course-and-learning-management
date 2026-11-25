package com.lms.backend.service.impl;

import com.lms.backend.entity.Category;
import com.lms.backend.repository.CategoryRepository;
import com.lms.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category createCategory(String name, String slug, String description, String iconName) {
        Category category = Category.builder()
                .name(name)
                .slug(slug)
                .description(description)
                .iconName(iconName)
                .build();
        return categoryRepository.save(category);
    }
}
