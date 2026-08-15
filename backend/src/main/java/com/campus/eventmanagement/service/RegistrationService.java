package com.campus.eventmanagement.service;

import com.campus.eventmanagement.entity.Event;
import com.campus.eventmanagement.entity.Registration;
import com.campus.eventmanagement.entity.User;
import com.campus.eventmanagement.exception.ConflictException;
import com.campus.eventmanagement.exception.ResourceNotFoundException;
import com.campus.eventmanagement.repository.EventRepository;
import com.campus.eventmanagement.repository.RegistrationRepository;
import com.campus.eventmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional
    public Registration rsvp(Long eventId, String attendeeEmail) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found: " + eventId));

        User attendee = userRepository.findByEmail(attendeeEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        registrationRepository.findByEventIdAndAttendeeId(eventId, attendee.getId())
                .ifPresent(r -> { throw new ConflictException("You have already RSVP'd to this event"); });

        long currentCount = registrationRepository.countByEventId(eventId);
        if (currentCount >= event.getCapacity()) {
            throw new ConflictException("This event is at full capacity");
        }

        Registration registration = Registration.builder()
                .event(event)
                .attendee(attendee)
                .build();

        return registrationRepository.save(registration);
    }

    public void cancelRsvp(Long eventId, String attendeeEmail) {
        User attendee = userRepository.findByEmail(attendeeEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Registration registration = registrationRepository
                .findByEventIdAndAttendeeId(eventId, attendee.getId())
                .orElseThrow(() -> new ResourceNotFoundException("You have not RSVP'd to this event"));

        registrationRepository.delete(registration);
    }
}
