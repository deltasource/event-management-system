package eu.deltasource.event_system.repository;

import eu.deltasource.event_system.model.Event;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class EventRepository implements BaseRepository<Event> {
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
