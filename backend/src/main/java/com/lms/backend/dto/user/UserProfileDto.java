package com.lms.backend.dto.user;

import com.lms.backend.enums.AccountStatus;
import com.lms.backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private UUID id;
    private String email;
    private String fullName;
    private String avatarUrl;
    private Role role;
    private AccountStatus accountStatus;
    private String bio;
    private String headline;
    private Instant createdAt;
}
