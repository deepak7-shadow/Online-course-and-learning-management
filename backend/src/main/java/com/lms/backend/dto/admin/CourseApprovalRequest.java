package com.lms.backend.dto.admin;

import com.lms.backend.enums.ApprovalDecision;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseApprovalRequest {
    @NotNull
    private ApprovalDecision decision;
    private String comments;
}
