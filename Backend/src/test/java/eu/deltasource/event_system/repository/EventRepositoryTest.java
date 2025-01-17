package eu.deltasource.event_system.repository;

import eu.deltasource.event_system.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventRepositoryTest {
    private EventRepository eventRepository;

    @BeforeEach
    public void setUp() {
        Map<UUID, Event> eventsMap = new HashMap<>();
        eventRepository = new EventRepository(eventsMap);
    }

    @Test
    public void saveEvent() {
        //Given
        Event event = new Event(UUID.randomUUID(), "Event", LocalDateTime.now(), "venue", 100, "details", 10);

        //When
        eventRepository.save(event);

        //Then
        Event actualEvent = eventRepository.getAll().get(0);
        assertEquals(1, eventRepository.getAll().size());
        assertEquals(event.getId(), actualEvent.getId());
        assertEquals(event.getName(), actualEvent.getName());
        assertEquals(event.getDateTime(), actualEvent.getDateTime());
        assertEquals(event.getVenue(), actualEvent.getVenue());
        assertEquals(event.getMaxCapacity(), actualEvent.getMaxCapacity());
        assertEquals(event.getTicketPrice(), actualEvent.getTicketPrice());
    }

    @Test
    public void getAllEvents_whenListIsNotEmpty() {
        //Given
        Event event = new Event(UUID.randomUUID(), "Event", LocalDateTime.now(), "venue", 100, "details", 10);

        //When
        eventRepository.save(event);

        //Then
        List<Event> eventList = eventRepository.getAll();
        assertEquals(1, eventList.size());
    }
}
