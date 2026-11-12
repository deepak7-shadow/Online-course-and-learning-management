package com.lms.security;

import com.lms.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;
import java.util.UUID;

public final class SecurityUtils {

    private SecurityUtils() {}

    /**
     * Extracts the Supabase authenticated user UUID from the security context.
     * The UUID is guaranteed to match auth.users.id via the verified JWT subject claim.
     */
    public static UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt jwt) {
            String subject = jwt.getSubject();
            if (subject == null || subject.isBlank()) {
                throw new UnauthorizedException("Invalid JWT subject identifier");
            }
            try {
                return UUID.fromString(subject);
            } catch (IllegalArgumentException e) {
                throw new UnauthorizedException("JWT subject is not a valid UUID: " + subject);
            }
        }

        if (principal instanceof String principalStr) {
            try {
                return UUID.fromString(principalStr);
            } catch (IllegalArgumentException e) {
                throw new UnauthorizedException("Principal is not a valid UUID string: " + principalStr);
            }
        }

        throw new UnauthorizedException("Unable to extract UUID from principal: " + principal);
    }

    /**
     * Extracts user email from JWT claims if available.
     */
    public static Optional<String> getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            String email = jwt.getClaimAsString("email");
            return Optional.ofNullable(email);
        }
        return Optional.empty();
    }

    /**
     * Checks if current user has a specific authority/role.
     */
    public static boolean hasRole(String roleName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return false;
        String expected = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equalsIgnoreCase(expected));
    }
}
