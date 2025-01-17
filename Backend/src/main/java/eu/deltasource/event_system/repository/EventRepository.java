package eu.deltasource.event_system.repository;

import eu.deltasource.event_system.model.Event;

import java.util.Map;
import java.util.UUID;

public class EventRepository implements Repository<Event>{
    private final Map<UUID, Event> events;

    public EventRepository(Map<UUID,Event> events) {
        this.events = events;
    }

    @Override
    public void save(Event event) {
        events.put(event.getId(), event);
    }
}
