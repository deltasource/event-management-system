package eu.deltasource.event_system.utils;

import eu.deltasource.event_system.model.Event;

import java.util.List;

/**
 * A helper class that wraps the fetched data from a .json file
 */
public class EventListWrapper {
    private final List<Event> events;

    public EventListWrapper(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}
