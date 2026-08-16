package com.campus.eventmanagement.repository;

import com.campus.eventmanagement.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganizerId(Long organizerId);
    List<Event> findByStatus(Event.EventStatus status);
}
