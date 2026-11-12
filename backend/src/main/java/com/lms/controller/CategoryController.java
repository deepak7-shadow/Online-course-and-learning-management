package com.lms.controller;

import com.lms.dto.category.CategoryDto;
import com.lms.dto.common.ApiResponse;
import com.lms.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDto.Response>>> getActiveCategories() {
        List<CategoryDto.Response> categories = categoryService.getActiveCategories();
        return ResponseEntity.ok(ApiResponse.ok("Active categories retrieved successfully", categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto.Response>> getCategoryById(@PathVariable UUID id) {
        CategoryDto.Response category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(ApiResponse.ok("Category retrieved successfully", category));
    }
}

