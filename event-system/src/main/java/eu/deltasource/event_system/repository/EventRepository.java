package eu.deltasource.event_system.repository;

import eu.deltasource.event_system.model.Event;

import java.util.List;

public class EventRepository implements Repository<Event>{
    private final List<Event> events;

    public EventRepository(List<Event> events) {
        this.events = events;
    }

    @Override
    public void save(Event event) {
        events.add(event);
    }
}
