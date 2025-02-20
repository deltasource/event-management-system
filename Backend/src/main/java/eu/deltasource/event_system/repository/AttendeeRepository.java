package eu.deltasource.event_system.repository;

import eu.deltasource.event_system.model.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttendeeRepository extends JpaRepository<Attendee, UUID> {
}
