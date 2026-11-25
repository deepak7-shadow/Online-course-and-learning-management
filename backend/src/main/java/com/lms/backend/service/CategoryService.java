package com.lms.backend.service;

import com.lms.backend.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category createCategory(String name, String slug, String description, String iconName);
}
