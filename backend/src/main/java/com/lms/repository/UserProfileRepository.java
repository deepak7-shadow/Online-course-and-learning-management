package com.lms.repository;

import com.lms.entity.UserProfile;
import com.lms.enums.AccountStatus;
import com.lms.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    Optional<UserProfile> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<UserProfile> findByRole(Role role, Pageable pageable);

    Page<UserProfile> findByStatus(AccountStatus status, Pageable pageable);

    Page<UserProfile> findByRoleAndStatus(Role role, AccountStatus status, Pageable pageable);

    long countByRole(Role role);

    long countByStatus(AccountStatus status);

    @Query("SELECT u FROM UserProfile u WHERE " +
           "(:query IS NULL OR :query = '' OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:role IS NULL OR u.role = :role) AND " +
           "(:status IS NULL OR u.status = :status)")
    Page<UserProfile> filterUsers(@Param("query") String query,
                                  @Param("role") Role role,
                                  @Param("status") AccountStatus status,
                                  Pageable pageable);
}

