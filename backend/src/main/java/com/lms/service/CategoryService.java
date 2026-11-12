package com.lms.service;

import com.lms.dto.category.CategoryDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryDto.Response> getActiveCategories();

    List<CategoryDto.Response> getAllCategories();

    CategoryDto.Response getCategoryById(UUID id);

    CategoryDto.Response createCategory(CategoryDto.Request request);

    CategoryDto.Response updateCategory(UUID id, CategoryDto.Request request);

    void deleteCategory(UUID id);
}

