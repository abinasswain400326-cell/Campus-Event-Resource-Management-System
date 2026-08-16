package com.campus.eventmanagement.dto;

import com.campus.eventmanagement.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    private String fullName;

    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // Defaults to ATTENDEE if not supplied — only an ADMIN can promote someone to ORGANIZER/ADMIN
    private Role role = Role.ATTENDEE;
}
