package eu.deltasource.event_system.repository;

import eu.deltasource.event_system.model.Event;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class EventRepository implements BaseRepository<Event> {
    private final Map<UUID, Event> events;

    public EventRepository(Map<UUID, Event> events) {
        this.events = events;
    }

    @Override
    public void save(Event event) {
        if (event.getId() == null) {
            UUID generatedUUID = getUUID();
            event.setId(generatedUUID);
        }
        events.put(event.getId(), event);
    }

    @Override
    public List<Event> getAll() {
        return events.values().stream().toList();
    }

    public Optional<Event> findById(UUID id) {
        return Optional.ofNullable(events.get(id));
    }

    public void delete(Event event) {
        events.remove(event.getId());
    }

    private UUID getUUID() {
        UUID generatedUUID = UUID.randomUUID();
        while (events.containsKey(generatedUUID)){
            generatedUUID = UUID.randomUUID();
        }
        return generatedUUID;
    }
}
