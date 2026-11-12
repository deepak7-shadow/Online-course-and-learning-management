package com.lms.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncProfileRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String fullName;

    private String avatarUrl;

    /**
     * Optional requested role (e.g. STUDENT or INSTRUCTOR).
     * ADMIN is strictly disallowed on untrusted client registration.
     */
    private String requestedRole;
}

