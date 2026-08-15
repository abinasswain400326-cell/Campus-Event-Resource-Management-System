package com.campus.eventmanagement.controller;

import com.campus.eventmanagement.dto.ResourceBookingRequest;
import com.campus.eventmanagement.entity.ResourceBooking;
import com.campus.eventmanagement.service.ResourceBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class ResourceBookingController {

    private final ResourceBookingService bookingService;

    @PostMapping
    public ResponseEntity<ResourceBooking> book(@Valid @RequestBody ResourceBookingRequest request) {
        return ResponseEntity.ok(bookingService.bookResource(request));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<ResourceBooking>> getForEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(bookingService.getBookingsForEvent(eventId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }
}
