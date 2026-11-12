package com.lms.config;

import com.lms.security.CustomAccessDeniedHandler;
import com.lms.security.JwtAuthConverter;
import com.lms.security.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Value("${supabase.jwt.secret:}")
    private String jwtSecret;

    @Value("${supabase.jwt.jwks-uri:}")
    private String jwksUri;

    @Value("${supabase.jwt.issuer:}")
    private String issuer;

    @Value("${app.cors.allowed-origins:http://localhost:5173,http://localhost:3000}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        // Public Endpoints
                        .requestMatchers("/error", "/actuator/**", "/favicon.ico").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/courses/**").permitAll()

                        // Admin Endpoints - Strict ADMIN role enforcement
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")

                        // Instructor Endpoints - INSTRUCTOR or ADMIN roles
                        .requestMatchers("/api/v1/instructor/**").hasAnyRole("INSTRUCTOR", "ADMIN")

                        // User & Student Profile Endpoints
                        .requestMatchers("/api/v1/users/**").authenticated()
                        .requestMatchers("/api/v1/student/**").hasAnyRole("STUDENT", "INSTRUCTOR", "ADMIN")

                        // Require authentication for all remaining requests
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder decoder;

        // Use symmetric HMAC secret if provided
        if (jwtSecret != null && !jwtSecret.isBlank() && !jwtSecret.startsWith("your-")) {
            log.info("Configuring Supabase JWT validation via configured Secret Key.");
            byte[] secretBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
            decoder = NimbusJwtDecoder.withSecretKey(secretKey).build();
        } else if (jwksUri != null && !jwksUri.isBlank() && !jwksUri.contains("your-project")) {
            log.info("Configuring Supabase JWT validation via JWKS URI: {}", jwksUri);
            decoder = NimbusJwtDecoder.withJwkSetUri(jwksUri).build();
        } else {
            // Development fallback mock secret key (for offline testing when Supabase env is unpopulated)
            log.warn("Supabase JWT secret or JWKS URI not specified. Using development fallback secret for token decoding.");
            String devFallback = "dev-secret-key-must-be-at-least-32-bytes-long-for-hmac-sha-256";
            SecretKeySpec devKey = new SecretKeySpec(devFallback.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            decoder = NimbusJwtDecoder.withSecretKey(devKey).build();
        }

        // Add custom token validator for Supabase audience claims
        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator("authenticated");
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(JwtValidators.createDefault(), audienceValidator));

        return decoder;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> origins = Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .toList();
        configuration.setAllowedOrigins(origins);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Disposition"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Custom Audience validator checking for Supabase 'aud' claim
     */
    private static class AudienceValidator implements OAuth2TokenValidator<Jwt> {
        private final String expectedAudience;

        public AudienceValidator(String expectedAudience) {
            this.expectedAudience = expectedAudience;
        }

        @Override
        public OAuth2TokenValidatorResult validate(Jwt jwt) {
            List<String> audiences = jwt.getAudience();
            if (audiences != null && audiences.contains(expectedAudience)) {
                return OAuth2TokenValidatorResult.success();
            }
            // Supabase default aud is "authenticated"
            return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", "The token audience does not match 'authenticated'", null));
        }
    }
}

