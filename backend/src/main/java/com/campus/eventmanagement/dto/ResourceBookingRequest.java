package com.campus.eventmanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResourceBookingRequest {

    @NotNull
    private Long resourceId;

    @NotNull
    private Long eventId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;
}
