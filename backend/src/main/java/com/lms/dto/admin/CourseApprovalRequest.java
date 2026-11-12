package com.lms.dto.admin;

import com.lms.enums.ApprovalDecision;
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

    @NotNull(message = "Approval decision is required (APPROVED or REJECTED)")
    private ApprovalDecision decision;

    private String comments;
}

