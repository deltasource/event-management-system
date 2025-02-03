package eu.deltasource.event_system.exceptions;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException() {
        super("This event is not found");
    }
}
