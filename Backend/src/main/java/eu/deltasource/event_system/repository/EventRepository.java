package eu.deltasource.event_system.repository;

import eu.deltasource.event_system.model.Event;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EventRepository implements eu.deltasource.event_system.repository.Repository<Event> {
    private final Map<UUID, Event> events;

    public EventRepository(Map<UUID, Event> events) {
        this.events = events;
    }

    @Override
    public void save(Event event) {
        events.put(event.getId(), event);
    }

    @Override
    public List<Event> getAll() {
        return events.values().stream().toList();
    }
}
