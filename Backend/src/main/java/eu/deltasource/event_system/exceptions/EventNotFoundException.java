package eu.deltasource.event_system.exceptions;

import java.util.UUID;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(UUID id) {
        super("Event with id " + id + " is not found");
    }
}
