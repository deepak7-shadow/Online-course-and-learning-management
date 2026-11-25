package com.lms.backend.dto.course;

import com.lms.backend.dto.module.ModuleDto;
import com.lms.backend.enums.CourseLevel;
import com.lms.backend.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailDto {
    private UUID id;
    private String title;
    private String slug;
    private String subtitle;
    private String description;
    private UUID categoryId;
    private String categoryName;
    private UUID instructorId;
    private String instructorName;
    private String instructorBio;
    private BigDecimal price;
    private CourseLevel level;
    private CourseStatus status;
    private String thumbnailUrl;
    private String promoVideoUrl;
    private List<ModuleDto> modules;
    private Instant createdAt;
}
