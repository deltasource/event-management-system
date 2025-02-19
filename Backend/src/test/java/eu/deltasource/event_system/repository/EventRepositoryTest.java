package eu.deltasource.event_system.repository;

import eu.deltasource.event_system.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EventRepositoryTest {
    private EventRepository eventRepository;

    @BeforeEach
    public void setUp() {
        Map<UUID, Event> eventsMap = new HashMap<>();
        eventRepository = new EventRepository(eventsMap);
    }

    @Test
    public void saveEvent_whenUUIDIsNotNull() {
        //Given
        Event event = new Event(UUID.randomUUID(), "Event", LocalDateTime.now(), "venue", 100, "details", 10, new ArrayList<>());

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
    public void saveEvent_whenUUIDIsNull() {
        //Given
        Event event = new Event(null, "Event", LocalDateTime.now(), "venue", 100, "details", 10, new ArrayList<>());

        //When
        eventRepository.save(event);

        //Then
        Event actualEvent = eventRepository.getAll().get(0);
        assertEquals(1, eventRepository.getAll().size());
        assertNotNull(actualEvent.getId());
        assertEquals(event.getName(), actualEvent.getName());
        assertEquals(event.getDateTime(), actualEvent.getDateTime());
        assertEquals(event.getVenue(), actualEvent.getVenue());
        assertEquals(event.getMaxCapacity(), actualEvent.getMaxCapacity());
        assertEquals(event.getTicketPrice(), actualEvent.getTicketPrice());
    }

    @Test
    public void getAllEvents_whenListIsNotEmpty() {
        //Given
        Event event = new Event(UUID.randomUUID(), "Event", LocalDateTime.now(), "venue", 100, "details", 10, new ArrayList<>());

        //When
        eventRepository.save(event);

        //Then
        List<Event> eventList = eventRepository.getAll();
        assertEquals(1, eventList.size());
    }

    @Test
    void findById_returnsEventWhenExists() {
        // Given
        UUID eventId = UUID.randomUUID();
        Event event = new Event(eventId, "Concert", LocalDateTime.now(), "Stadium", 100, "Organizer", 20.0, new ArrayList<>());
        eventRepository.save(event);

        // When
        Optional<Event> foundEvent = eventRepository.findById(eventId);

        // Then
        assertTrue(foundEvent.isPresent());
        assertEquals(event, foundEvent.get());
    }

    @Test
    void delete_removesEvent() {
        // Given
        UUID eventId = UUID.randomUUID();
        Event event = new Event(eventId, "Concert", LocalDateTime.now(), "Stadium", 100, "Organizer", 20.0, new ArrayList<>());
        eventRepository.save(event);

        // When
        eventRepository.delete(event);

        // Then
        assertTrue(eventRepository.findById(eventId).isEmpty());
    }
}
