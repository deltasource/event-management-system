package eu.deltasource.event_system.repository;

import eu.deltasource.event_system.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
