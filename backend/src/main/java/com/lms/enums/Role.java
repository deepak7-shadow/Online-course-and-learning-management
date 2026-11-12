package com.lms.enums;

public enum Role {
    ROLE_STUDENT,
    ROLE_INSTRUCTOR,
    ROLE_ADMIN;

    public static Role fromString(String roleStr) {
        if (roleStr == null || roleStr.isBlank()) {
            return ROLE_STUDENT;
        }
        String normalized = roleStr.trim().toUpperCase();
        if (!normalized.startsWith("ROLE_")) {
            normalized = "ROLE_" + normalized;
        }
        for (Role r : values()) {
            if (r.name().equals(normalized)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + roleStr);
    }
}

