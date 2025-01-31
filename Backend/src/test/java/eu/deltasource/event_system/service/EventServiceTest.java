package eu.deltasource.event_system.service;

import eu.deltasource.EventMapper;
import eu.deltasource.event_system.dto.AddEventDto;
import eu.deltasource.event_system.dto.EventViewDto;
import eu.deltasource.event_system.exceptions.EventNotFoundException;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    @Test
    public void addEvent() {
        //Given
        AddEventDto addEventDto = new AddEventDto("Event", LocalDateTime.now(), "venue", 100, "details", 10);
        Event event = new Event(UUID.randomUUID(), "Event", LocalDateTime.now(), "venue", 100, "details", 10);
        when(eventMapper.mapFromTo(addEventDto, Event.class))
                .thenReturn(event);

        //When
        eventService.add(addEventDto);

        //Then
        ArgumentCaptor<Event> eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(eventArgumentCaptor.capture());
        Event savedEvent = eventArgumentCaptor.getValue();
        assertEquals(event.getName(), savedEvent.getName());
        assertEquals(event.getMaxCapacity(), savedEvent.getMaxCapacity());
        assertEquals(event.getVenue(), savedEvent.getVenue());
        assertEquals(event.getDateTime(), savedEvent.getDateTime());
        assertEquals(event.getOrganizerDetails(), savedEvent.getOrganizerDetails());
    }

    @Test
    public void deleteEventSuccessfully() {
        //Given
        Event event = new Event(UUID.randomUUID(), "Event", LocalDateTime.now(), "venue", 100, "details", 10);
        UUID uuid = UUID.randomUUID();
        when(eventRepository.findById(uuid))
                .thenReturn(Optional.of(event));

        //When
        eventService.delete(uuid);

        //Then
        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    public void deleteEventFails_whenEventIsNotFound() {
        //Given
        UUID uuid = UUID.randomUUID();
        when(eventRepository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        //When, Then
        assertThrows(EventNotFoundException.class, () -> {
            eventService.delete(uuid);
        });
    }

    @Test
    public void updateEventSuccessfully() {
        //Given
        UUID uuid = UUID.randomUUID();
        AddEventDto addEventDto = new AddEventDto("Event", LocalDateTime.now(), "venue", 100, "details", 10);
        Event event = new Event(uuid, "Event", LocalDateTime.now(), "venue", 100, "details", 10);
        when(eventRepository.findById(uuid))
                .thenReturn(Optional.of(event));

        //When
        eventService.updateEvent(uuid, addEventDto);

        //Then
        assertEquals(event.getName(), addEventDto.name());
        assertEquals(event.getMaxCapacity(), addEventDto.maxCapacity());
        assertEquals(event.getVenue(), addEventDto.venue());
        assertEquals(event.getDateTime(), addEventDto.dateTime());
        assertEquals(event.getOrganizerDetails(), addEventDto.organizerDetails());
    }
}
