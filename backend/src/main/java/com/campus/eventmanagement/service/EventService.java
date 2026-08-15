package com.campus.eventmanagement.service;

import com.campus.eventmanagement.dto.EventRequest;
import com.campus.eventmanagement.dto.EventResponse;
import com.campus.eventmanagement.entity.Event;
import com.campus.eventmanagement.entity.User;
import com.campus.eventmanagement.exception.ResourceNotFoundException;
import com.campus.eventmanagement.repository.EventRepository;
import com.campus.eventmanagement.repository.RegistrationRepository;
import com.campus.eventmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;

    public EventResponse createEvent(EventRequest request, String organizerEmail) {
        User organizer = userRepository.findByEmail(organizerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found"));

        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .venue(request.getVenue())
                .capacity(request.getCapacity())
                .organizer(organizer)
                .status(Event.EventStatus.PUBLISHED)
                .build();

        event = eventRepository.save(event);
        return toResponse(event);
    }

    public List<EventResponse> getAllPublishedEvents() {
        return eventRepository.findByStatus(Event.EventStatus.PUBLISHED)
                .stream().map(this::toResponse).toList();
    }

    public EventResponse getEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found: " + id));
        return toResponse(event);
    }

    public EventResponse updateEvent(Long id, EventRequest request, String requesterEmail) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found: " + id));

        assertOwnerOrAdmin(event, requesterEmail);

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setVenue(request.getVenue());
        event.setCapacity(request.getCapacity());

        return toResponse(eventRepository.save(event));
    }

    public void cancelEvent(Long id, String requesterEmail) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found: " + id));
        assertOwnerOrAdmin(event, requesterEmail);
        event.setStatus(Event.EventStatus.CANCELLED);
        eventRepository.save(event);
    }

    private void assertOwnerOrAdmin(Event event, String requesterEmail) {
        User requester = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isOwner = event.getOrganizer().getId().equals(requester.getId());
        boolean isAdmin = requester.getRole().name().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new IllegalArgumentException("You do not have permission to modify this event");
        }
    }

    private EventResponse toResponse(Event event) {
        long count = registrationRepository.countByEventId(event.getId());
        return new EventResponse(
                event.getId(), event.getTitle(), event.getDescription(),
                event.getStartTime(), event.getEndTime(), event.getVenue(),
                event.getCapacity(), count, event.getOrganizer().getFullName(), event.getStatus()
        );
    }
}
