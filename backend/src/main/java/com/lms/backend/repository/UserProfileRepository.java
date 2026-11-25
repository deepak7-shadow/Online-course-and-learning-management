package com.lms.backend.repository;

import com.lms.backend.entity.UserProfile;
import com.lms.backend.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    Optional<UserProfile> findByEmail(String email);
    Page<UserProfile> findByRole(Role role, Pageable pageable);
    long countByRole(Role role);
}
