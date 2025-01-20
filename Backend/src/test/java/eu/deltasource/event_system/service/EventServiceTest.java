package eu.deltasource.event_system.service;

import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private EventService eventService;

    @Test
    public void getEvents() {
        //Given
        Event event = new Event(UUID.randomUUID(), "Event", LocalDateTime.now(), "venue", 100, "details", 10);
        List<Event> events = List.of(event);
        Mockito.when(eventRepository.getAll())
                .thenReturn(events);

        //When
        List<Event> allEvents = eventService.getAllEvents();

        //Then
        assertEquals(events.size(), allEvents.size());
        assertEquals(events.get(0), allEvents.get(0));
    }
}
