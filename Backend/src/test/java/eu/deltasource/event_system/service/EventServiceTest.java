package eu.deltasource.event_system.service;

import eu.deltasource.EventMapper;
import eu.deltasource.event_system.dto.EventViewDto;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventMapper eventMapper;
    @InjectMocks
    private EventService eventService;

    @Test
    public void getEvents() {
        //Given
        Event event = new Event(UUID.randomUUID(), "Event", LocalDateTime.now(), "venue", 100, "details", 10);
        EventViewDto eventViewDto = new EventViewDto(event.getName(), event.getVenue(), event.getDateTime().toString(), event.getTicketPrice());
        List<Event> events = List.of(event);
        when(eventRepository.getAll())
                .thenReturn(events);
        when(eventMapper.mapFromTo(any(Event.class), eq(EventViewDto.class)))
                .thenReturn(eventViewDto);

        //When
        List<EventViewDto> allEvents = eventService.getAllEvents();

        //Then
        assertEquals(events.size(), allEvents.size());
        assertEquals(events.get(0).getName(), allEvents.get(0).name());
    }
}
