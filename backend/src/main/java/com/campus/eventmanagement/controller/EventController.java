package com.campus.eventmanagement.controller;

import com.campus.eventmanagement.dto.EventRequest;
import com.campus.eventmanagement.dto.EventResponse;
import com.campus.eventmanagement.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllPublishedEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    // ORGANIZER or ADMIN only (enforced in SecurityConfig)
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest request,
                                                       Authentication auth) {
        return ResponseEntity.ok(eventService.createEvent(request, auth.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long id,
                                                       @Valid @RequestBody EventRequest request,
                                                       Authentication auth) {
        return ResponseEntity.ok(eventService.updateEvent(id, request, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelEvent(@PathVariable Long id, Authentication auth) {
        eventService.cancelEvent(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}
