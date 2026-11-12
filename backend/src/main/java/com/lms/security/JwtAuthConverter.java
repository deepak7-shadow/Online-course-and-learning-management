package com.lms.security;

import com.lms.entity.UserProfile;
import com.lms.enums.AccountStatus;
import com.lms.exception.AccountSuspendedException;
import com.lms.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserProfileRepository userProfileRepository;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String subject = jwt.getSubject();
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("JWT subject claim (sub) is missing or blank");
        }

        UUID userUuid;
        try {
            userUuid = UUID.fromString(subject);
        } catch (IllegalArgumentException e) {
            log.error("JWT sub claim is not a valid UUID: {}", subject);
            throw new IllegalArgumentException("Invalid UUID in JWT sub claim: " + subject, e);
        }

        Set<GrantedAuthority> authorities = new HashSet<>();

        // Lookup user profile in database to enforce verified roles and account status
        Optional<UserProfile> profileOpt = userProfileRepository.findById(userUuid);
        if (profileOpt.isPresent()) {
            UserProfile profile = profileOpt.get();

            // Verify account status
            if (profile.getStatus() == AccountStatus.SUSPENDED) {
                log.warn("Access rejected for suspended user ID: {}", userUuid);
                throw new AccountSuspendedException("Account for user " + userUuid + " is currently suspended.");
            }

            // Assign role from database (trusted authority)
            authorities.add(new SimpleGrantedAuthority(profile.getRole().name()));
        } else {
            // New user authenticated with Supabase whose profile hasn't synced yet
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }

        // Return token with subject UUID as principal name
        return new JwtAuthenticationToken(jwt, authorities, subject);
    }
}

