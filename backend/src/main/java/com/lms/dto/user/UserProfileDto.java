package com.lms.dto.user;

import com.lms.entity.UserProfile;
import com.lms.enums.AccountStatus;
import com.lms.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
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
    private AccountStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static UserProfileDto fromEntity(UserProfile entity) {
        if (entity == null) return null;
        return UserProfileDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .avatarUrl(entity.getAvatarUrl())
                .role(entity.getRole())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
