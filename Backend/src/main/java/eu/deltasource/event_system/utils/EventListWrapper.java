package eu.deltasource.event_system.utils;

import eu.deltasource.event_system.model.Event;

import java.util.List;

/**
 * A helper class that wraps the fetched data from a .json file
 */
public class EventListWrapper {
    private List<Event> events;

    public EventListWrapper() {
    }

    public EventListWrapper(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
