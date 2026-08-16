package com.campus.eventmanagement.dto;

import com.campus.eventmanagement.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String venue;
    private Integer capacity;
    private long registeredCount;
    private String organizerName;
    private Event.EventStatus status;
}
