package com.campus.eventmanagement.dto;

import com.campus.eventmanagement.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long userId;
    private String fullName;
    private String email;
    private Role role;
}
