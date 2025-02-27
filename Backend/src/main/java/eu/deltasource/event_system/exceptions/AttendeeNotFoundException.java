package eu.deltasource.event_system.exceptions;

import java.util.UUID;

public class AttendeeNotFoundException extends RuntimeException {
    public AttendeeNotFoundException(UUID id) {
        super("Attendee with id " + id + " is not found");
    }
}
