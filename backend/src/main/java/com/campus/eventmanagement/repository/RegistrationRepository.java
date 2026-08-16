package com.campus.eventmanagement.repository;

import com.campus.eventmanagement.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByEventId(Long eventId);
    List<Registration> findByAttendeeId(Long attendeeId);
    Optional<Registration> findByEventIdAndAttendeeId(Long eventId, Long attendeeId);
    long countByEventId(Long eventId);
}
