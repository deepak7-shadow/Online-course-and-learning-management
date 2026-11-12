package com.lms.controller;

import com.lms.dto.category.CategoryDto;
import com.lms.dto.common.ApiResponse;
import com.lms.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDto.Response>>> listAllCategories() {
        List<CategoryDto.Response> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.ok("Categories retrieved successfully", categories));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDto.Response>> createCategory(
            @Valid @RequestBody CategoryDto.Request request) {
        CategoryDto.Response created = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Category created successfully", created));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDto.Response>> updateCategory(
            @PathVariable UUID categoryId,
            @Valid @RequestBody CategoryDto.Request request) {
        CategoryDto.Response updated = categoryService.updateCategory(categoryId, request);
        return ResponseEntity.ok(ApiResponse.ok("Category updated successfully", updated));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.ok("Category deleted successfully", null));
    }
}
