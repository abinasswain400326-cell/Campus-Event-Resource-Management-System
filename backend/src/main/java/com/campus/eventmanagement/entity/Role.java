package com.campus.eventmanagement.entity;

public enum Role {
    ADMIN,      // full control: manage users, all events, all resources
    ORGANIZER,  // can create/manage their own events and book resources
    ATTENDEE    // can browse events and RSVP
}
