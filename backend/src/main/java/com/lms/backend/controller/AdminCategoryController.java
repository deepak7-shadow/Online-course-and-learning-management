package com.lms.backend.controller;

import com.lms.backend.dto.common.ApiResponse;
import com.lms.backend.entity.Category;
import com.lms.backend.service.CategoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @Data
    public static class CreateCategoryDto {
        private String name;
        private String slug;
        private String description;
        private String iconName;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody CreateCategoryDto dto) {
        return ResponseEntity.ok(ApiResponse.ok(
                categoryService.createCategory(dto.getName(), dto.getSlug(), dto.getDescription(), dto.getIconName())
        ));
    }
}
