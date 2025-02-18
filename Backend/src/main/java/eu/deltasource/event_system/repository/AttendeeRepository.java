package eu.deltasource.event_system.repository;

import eu.deltasource.event_system.model.Attendee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AttendeeRepository implements BaseRepository<Attendee> {
    private final Map<UUID, Attendee> attendees;

    public AttendeeRepository(Map<UUID, Attendee> attendees) {
        this.attendees = attendees;
    }

    @Override
    public void save(Attendee attendee) {
        if (attendee.getId() == null) {
            UUID generatedUUID = generateId();
            attendee.setId(generatedUUID);
        }
        attendees.put(attendee.getId(), attendee);
    }

    @Override
    public List<Attendee> getAll() {
        return attendees.values().stream().toList();
    }

    public Optional<Attendee> findById(UUID id) {
        return Optional.ofNullable(attendees.get(id));
    }

    private UUID generateId() {
        UUID generatedUUID = UUID.randomUUID();
        while (attendees.containsKey(generatedUUID)) {
            generatedUUID = UUID.randomUUID();
        }
        return generatedUUID;
    }
}
