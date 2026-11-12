package com.lms.repository;

import com.lms.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, UUID> {
    Optional<Certificate> findByEnrollmentId(UUID enrollmentId);
    Optional<Certificate> findByCertificateCode(String code);
    boolean existsByEnrollmentId(UUID enrollmentId);
}

