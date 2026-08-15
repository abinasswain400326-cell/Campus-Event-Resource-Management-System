package com.campus.eventmanagement.controller;

import com.campus.eventmanagement.entity.Registration;
import com.campus.eventmanagement.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events/{eventId}/rsvp")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<Registration> rsvp(@PathVariable Long eventId, Authentication auth) {
        return ResponseEntity.ok(registrationService.rsvp(eventId, auth.getName()));
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelRsvp(@PathVariable Long eventId, Authentication auth) {
        registrationService.cancelRsvp(eventId, auth.getName());
        return ResponseEntity.noContent().build();
    }
}
