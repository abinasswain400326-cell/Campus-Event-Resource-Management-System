package com.campus.eventmanagement.exception;

// Thrown when a resource booking would overlap an existing one, or a duplicate RSVP is attempted.
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
