package com.lms.backend.controller;

import com.lms.backend.dto.common.ApiResponse;
import com.lms.backend.dto.learning.CertificateDto;
import com.lms.backend.security.SecurityUtils;
import com.lms.backend.service.LearningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/learning/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final LearningService learningService;

    @GetMapping("/{code}")
    public ResponseEntity<ApiResponse<CertificateDto>> getCertificate(@PathVariable String code) {
        return ResponseEntity.ok(ApiResponse.ok(learningService.getCertificateByCode(code)));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<CertificateDto>>> getMyCertificates() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.ok(learningService.getStudentCertificates(userId)));
    }
}
