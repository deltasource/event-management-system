package eu.deltasource.event_system.service;

import eu.deltasource.EventMapper;
import eu.deltasource.dto.CreateEventDto;
import eu.deltasource.dto.EventViewDto;
import eu.deltasource.event_system.exceptions.EventNotFoundException;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.validation.Validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    @Mock
    private Validator validator;
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
    public void createEventSuccessfully() {
        //Given
        CreateEventDto createEventDto = new CreateEventDto("Event", LocalDateTime.now(), "venue", 100, "details", 10);
        Event event = new Event(UUID.randomUUID(), "Event", createEventDto.dateTime(), "venue", 100, "details", 10);
        when(eventMapper.mapFromTo(createEventDto, Event.class))
                .thenReturn(event);
        when(validator.validate(event))
                .thenReturn(Set.of());

        //When
        eventService.create(createEventDto);

        //Then
        ArgumentCaptor<Event> eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(eventArgumentCaptor.capture());
        Event savedEvent = eventArgumentCaptor.getValue();
        assertEquals(event, savedEvent);
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
        when(eventRepository.findById(uuid))
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
        CreateEventDto createEventDto = new CreateEventDto("Event", LocalDateTime.now(), "venue", 100, "details", 10);
        Event event = new Event(uuid, "Event", LocalDateTime.now(), "venue", 100, "details", 10);
        Event updatedEvent = new Event(uuid, "Event1", LocalDateTime.now(), "venue", 100, "details", 10);
        when(eventRepository.findById(uuid))
                .thenReturn(Optional.of(event));
        when(eventMapper.mapFromTo(createEventDto, Event.class))
                .thenReturn(updatedEvent);
        lenient().when(validator.validate(updatedEvent))
                .thenReturn(Set.of());

        //When
        eventService.updateEvent(uuid, createEventDto);

        //Then
        assertEquals(event.getName(), updatedEvent.getName());
        assertEquals(event.getMaxCapacity(), updatedEvent.getMaxCapacity());
        assertEquals(event.getVenue(), updatedEvent.getVenue());
        assertEquals(event.getDateTime(), updatedEvent.getDateTime());
        assertEquals(event.getOrganizerDetails(), updatedEvent.getOrganizerDetails());
    }
}
