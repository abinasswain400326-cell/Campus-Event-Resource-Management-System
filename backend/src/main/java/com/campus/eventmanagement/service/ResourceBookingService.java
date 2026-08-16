package com.campus.eventmanagement.service;

import com.campus.eventmanagement.dto.ResourceBookingRequest;
import com.campus.eventmanagement.entity.Event;
import com.campus.eventmanagement.entity.Resource;
import com.campus.eventmanagement.entity.ResourceBooking;
import com.campus.eventmanagement.exception.ConflictException;
import com.campus.eventmanagement.exception.ResourceNotFoundException;
import com.campus.eventmanagement.repository.EventRepository;
import com.campus.eventmanagement.repository.ResourceBookingRepository;
import com.campus.eventmanagement.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceBookingService {

    private final ResourceBookingRepository bookingRepository;
    private final ResourceRepository resourceRepository;
    private final EventRepository eventRepository;

    /**
     * Books a resource (venue/equipment) for an event, after checking for time-window
     * conflicts against every other booking of that same resource. This is the
     * "automated conflict detection between overlapping events" feature.
     */
    @Transactional
    public ResourceBooking bookResource(ResourceBookingRequest request) {
        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        Resource resource = resourceRepository.findById(request.getResourceId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        List<ResourceBooking> overlaps = bookingRepository.findOverlapping(
                resource.getId(), request.getStartTime(), request.getEndTime()
        );

        if (!overlaps.isEmpty()) {
            ResourceBooking conflict = overlaps.get(0);
            throw new ConflictException(
                    "Resource '" + resource.getName() + "' is already booked for event '"
                            + conflict.getEvent().getTitle() + "' from "
                            + conflict.getStartTime() + " to " + conflict.getEndTime()
            );
        }

        ResourceBooking booking = ResourceBooking.builder()
                .resource(resource)
                .event(event)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        return bookingRepository.save(booking);
    }

    public List<ResourceBooking> getBookingsForEvent(Long eventId) {
        return bookingRepository.findByEventId(eventId);
    }

    public void cancelBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new ResourceNotFoundException("Booking not found: " + bookingId);
        }
        bookingRepository.deleteById(bookingId);
    }
}
