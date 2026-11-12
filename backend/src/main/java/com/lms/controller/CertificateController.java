package com.lms.controller;

import com.lms.dto.common.ApiResponse;
import com.lms.dto.learning.CertificateDto;
import com.lms.service.LearningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final LearningService learningService;

    @GetMapping("/verify/{code}")
    public ResponseEntity<ApiResponse<CertificateDto>> verifyCertificate(@PathVariable String code) {
        CertificateDto cert = learningService.getCertificateByCode(code);
        return ResponseEntity.ok(ApiResponse.ok("Certificate is valid", cert));
    }
}

