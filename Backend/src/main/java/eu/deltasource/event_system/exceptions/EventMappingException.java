package eu.deltasource.event_system.exceptions;

public class EventMappingException extends RuntimeException {
    public EventMappingException() {
        super("An error occurred while updating the event data. Please try again.");
    }
}
