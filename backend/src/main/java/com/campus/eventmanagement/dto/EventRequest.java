package com.campus.eventmanagement.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotBlank
    private String venue;

    @NotNull @Positive
    private Integer capacity;
}
