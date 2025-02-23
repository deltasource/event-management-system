package eu.deltasource.event_system.utils.simulations;

import eu.deltasource.dto.CreateEventDto;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import eu.deltasource.event_system.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class DeadlockSimulation {

    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate; // ✅ Inject JdbcTemplate to query PostgreSQL

    private static final Logger logger = LoggerFactory.getLogger(DeadlockSimulation.class);

    @EventListener(ApplicationReadyEvent.class)
    public void triggerDeadlockOnStartup() {
        logger.info("🔥 Starting Deadlock Simulation...");

        // Create two events
        CreateEventDto eventDto1 = new CreateEventDto("Tech Conference", "2025-06-10T10:00", "Conference Hall A", 300, "Tech Corp", 100.0);
        CreateEventDto eventDto2 = new CreateEventDto("Music Festival", "2025-07-15T18:00", "Outdoor Stage", 5000, "Music Live", 75.0);

        String event1Name = eventService.create(eventDto1);
        String event2Name = eventService.create(eventDto2);

        logger.info("✅ Created Events: {} & {}", event1Name, event2Name);

        // Retrieve event IDs (force actual repository lookup)
        Event eventOne = eventRepository.findByName(event1Name);
        Event eventTwo = eventRepository.findByName(event2Name);

        CreateEventDto updateDto1 = new CreateEventDto("Updated Tech Conference", "2025-06-10T12:00", "Updated Hall A", 350, "Updated Tech Corp", 120.0);
        CreateEventDto updateDto2 = new CreateEventDto("Updated Music Festival", "2025-07-15T20:00", "Updated Outdoor Stage", 5500, "Updated Music Live", 90.0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 🔥 Transaction 1: Locks Event 1 first, then tries to lock Event 2
        executor.submit(() -> {
            try {
                logger.info("🔒 Transaction 1: Updating Event 1...");
                eventService.updateEvent(eventOne.getId(), updateDto1);
                logger.info("✅ Transaction 1: Successfully updated Event 1");

                Thread.sleep(1000); // 🔥 Ensure overlap

                logger.info("🔒 Transaction 1: Updating Event 2...");
                eventService.updateEvent(eventTwo.getId(), updateDto2);
            } catch (Exception e) {
                logger.error("❌ Transaction 1 failed: {}", e.getMessage());
            }
        });

        executor.submit(() -> {
            try {
                logger.info("🔒 Transaction 2: Updating Event 2...");
                eventService.updateEvent(eventTwo.getId(), updateDto2);
                logger.info("✅ Transaction 2: Successfully updated Event 2");

                Thread.sleep(1000); // 🔥 Ensure overlap

                logger.info("🔒 Transaction 2: Updating Event 1...");
                eventService.updateEvent(eventOne.getId(), updateDto1);
            } catch (Exception e) {
                logger.error("❌ Transaction 2 failed: {}", e.getMessage());
            }
        });

        executor.shutdown();
    }

    /**
     * Logs currently waiting PostgreSQL locks.
     */
    private void logPostgreLocks() {
        String sql = """
            SELECT pid, locktype, relation::regclass AS table_name, mode, granted
            FROM pg_locks
            WHERE NOT granted;
        """;

        List<Map<String, Object>> locks = jdbcTemplate.queryForList(sql);

        if (locks.isEmpty()) {
            logger.info("🔍 No waiting locks detected. PostgreSQL is executing transactions sequentially.");
        } else {
            logger.warn("🔒 PostgreSQL Lock Queue:");
            for (Map<String, Object> lock : locks) {
                logger.warn("⏳ Process {} is waiting for lock on table {} ({} mode)",
                        lock.get("pid"), lock.get("table_name"), lock.get("mode"));
            }
        }
    }
}
