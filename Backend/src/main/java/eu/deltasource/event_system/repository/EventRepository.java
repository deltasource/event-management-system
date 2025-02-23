package eu.deltasource.event_system.repository;

import eu.deltasource.event_system.model.Event;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    Event findByName(String event1Name);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // Forces row-level lock
    @Query("SELECT e FROM Event e WHERE e.id = :id")
    Event findByIdForUpdate(@Param("id") UUID id);
}
