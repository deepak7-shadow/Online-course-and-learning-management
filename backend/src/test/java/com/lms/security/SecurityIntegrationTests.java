package com.lms.security;

import com.lms.entity.UserProfile;
import com.lms.enums.AccountStatus;
import com.lms.enums.Role;
import com.lms.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private UUID adminId;
    private UUID studentId;
    private UUID suspendedUserId;

    @BeforeEach
    void setup() {
        userProfileRepository.deleteAll();

        adminId = UUID.randomUUID();
        UserProfile admin = UserProfile.builder()
                .id(adminId)
                .email("admin@lms.com")
                .fullName("System Administrator")
                .role(Role.ROLE_ADMIN)
                .status(AccountStatus.ACTIVE)
                .build();
        userProfileRepository.save(admin);

        studentId = UUID.randomUUID();
        UserProfile student = UserProfile.builder()
                .id(studentId)
                .email("student@lms.com")
                .fullName("Enrolled Student")
                .role(Role.ROLE_STUDENT)
                .status(AccountStatus.ACTIVE)
                .build();
        userProfileRepository.save(student);

        suspendedUserId = UUID.randomUUID();
        UserProfile suspended = UserProfile.builder()
                .id(suspendedUserId)
                .email("suspended@lms.com")
                .fullName("Suspended User")
                .role(Role.ROLE_STUDENT)
                .status(AccountStatus.SUSPENDED)
                .build();
        userProfileRepository.save(suspended);
    }

    @Test
    @DisplayName("Unauthenticated request to protected endpoint should return 401 Unauthorized")
    void testMissingTokenReturns401() throws Exception {
        mockMvc.perform(get("/api/v1/users/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    @DisplayName("Public categories endpoint should be accessible without authentication")
    void testPublicEndpointAccessibleWithoutToken() throws Exception {
        mockMvc.perform(get("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("Student attempting to access Admin endpoint should return 403 Forbidden")
    void testStudentRoleForbiddenOnAdminEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/admin/users")
                        .with(jwt().jwt(builder -> builder
                                .subject(studentId.toString())
                                .claim("email", "student@lms.com")
                                .claim("aud", "authenticated")
                        )))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("Admin accessing Admin endpoint should succeed with 200 OK")
    void testAdminAccessAllowed() throws Exception {
        mockMvc.perform(get("/api/v1/admin/users")
                        .with(jwt().jwt(builder -> builder
                                .subject(adminId.toString())
                                .claim("email", "admin@lms.com")
                                .claim("aud", "authenticated")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("Suspended user attempting to access API should return 403 Forbidden")
    void testSuspendedUserAccessForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/users/me")
                        .with(jwt().jwt(builder -> builder
                                .subject(suspendedUserId.toString())
                                .claim("email", "suspended@lms.com")
                                .claim("aud", "authenticated")
                        )))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.success").value(false));
    }
}
