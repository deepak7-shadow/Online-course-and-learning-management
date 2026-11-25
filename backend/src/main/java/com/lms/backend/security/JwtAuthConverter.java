package com.lms.backend.security;

import com.lms.backend.entity.UserProfile;
import com.lms.backend.enums.AccountStatus;
import com.lms.backend.enums.Role;
import com.lms.backend.exception.AccountSuspendedException;
import com.lms.backend.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserProfileRepository userProfileRepository;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String sub = jwt.getSubject();
        String email = jwt.getClaimAsString("email");

        if (sub != null) {
            try {
                UUID userId = UUID.fromString(sub);
                Optional<UserProfile> profileOpt = userProfileRepository.findById(userId);

                UserProfile profile;
                if (profileOpt.isEmpty()) {
                    // Create default profile for newly registered user from Supabase Auth
                    profile = UserProfile.builder()
                            .email(email != null ? email : sub + "@supabase.io")
                            .role(Role.STUDENT)
                            .accountStatus(AccountStatus.ACTIVE)
                            .fullName(jwt.getClaimAsString("full_name"))
                            .build();
                    profile.setId(userId);
                    profile = userProfileRepository.save(profile);
                } else {
                    profile = profileOpt.get();
                }

                if (profile.getAccountStatus() == AccountStatus.SUSPENDED) {
                    throw new AccountSuspendedException("Your account has been suspended by an administrator.");
                }

                authorities.add(new SimpleGrantedAuthority("ROLE_" + profile.getRole().name()));
            } catch (IllegalArgumentException ignored) {
                // Not a valid UUID
            }
        }

        // Add default role if none resolved
        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }

        return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
    }
}
