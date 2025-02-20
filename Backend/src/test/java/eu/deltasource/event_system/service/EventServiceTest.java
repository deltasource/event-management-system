package eu.deltasource.event_system.service;

import eu.deltasource.EntityMapper;
import eu.deltasource.dto.CreateEventDto;
import eu.deltasource.dto.EventDto;
import eu.deltasource.event_system.exceptions.EventNotFoundException;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.AttendeeRepository;
import eu.deltasource.event_system.repository.EventRepository;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

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
    private AttendeeRepository attendeeRepository;
    @Mock
    private EntityMapper entityMapper;
    @Mock
    private Validator validator;
    @InjectMocks
    private EventService eventService;

    @Test
    public void getEvents() {
        //Given
        Event event = new Event(UUID.randomUUID(), "Event", LocalDateTime.now(), "venue", 100, "details", 10, new ArrayList<>());
        EventDto eventViewDto = new EventDto(event.getId(), event.getName(), event.getVenue(), event.getDateTime().toString(), event.getMaxCapacity(), event.getOrganizerDetails(), event.getTicketPrice());
        List<Event> events = List.of(event);
        when(eventRepository.findAll())
                .thenReturn(events);
        when(entityMapper.mapFromTo(any(Event.class), eq(EventDto.class)))
                .thenReturn(eventViewDto);

        //When
        List<EventDto> allEvents = eventService.getAllEvents();

        //Then
        assertEquals(events.size(), allEvents.size());
        assertEquals(events.get(0).getName(), allEvents.get(0).name());
    }

    @Test
    public void createEventSuccessfully() {
        //Given
        CreateEventDto createEventDto = new CreateEventDto("Event", LocalDateTime.now().toString(), "venue", 100, "details", 10);
        Event event = new Event(UUID.randomUUID(), "Event", LocalDateTime.parse(createEventDto.dateTime()), "venue", 100, "details", 10, new ArrayList<>());
        when(entityMapper.mapFromTo(createEventDto, Event.class))
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
        Event event = new Event(UUID.randomUUID(), "Event", LocalDateTime.now(), "venue", 100, "details", 10, new ArrayList<>());
        UUID uuid = UUID.randomUUID();
        when(eventRepository.findById(uuid))
                .thenReturn(Optional.of(event));

        //When
        eventService.delete(uuid);

        //Then
        verify(eventRepository, times(1)).delete(event);
        verify(attendeeRepository, times(1)).deleteAll(event.getAttendees());
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
        // Given
        UUID uuid = UUID.randomUUID();
        CreateEventDto createEventDto = new CreateEventDto("Event1", LocalDateTime.now().toString(), "venue", 100, "details", 10);
        Event event = new Event(uuid, "Event", LocalDateTime.now(), "venue", 100, "details", 10, new ArrayList<>());
        Event updatedEvent = new Event(uuid, "Event1", LocalDateTime.now(), "venue", 100, "details", 10, new ArrayList<>());
        when(eventRepository.findById(uuid))
                .thenReturn(Optional.of(event));
        lenient().when(entityMapper.mapFromTo(createEventDto, Event.class))
                .thenReturn(updatedEvent);
        lenient().when(validator.validate(updatedEvent))
                .thenReturn(Set.of());

        // When
        eventService.updateEvent(uuid, createEventDto);

        // Then
        assertEquals(createEventDto.name(), event.getName());
        assertEquals(createEventDto.maxCapacity(), event.getMaxCapacity());
        assertEquals(createEventDto.venue(), event.getVenue());
        assertEquals(LocalDateTime.parse(createEventDto.dateTime()), event.getDateTime());
        assertEquals(createEventDto.organizerDetails(), event.getOrganizerDetails());
        verify(eventRepository, times(1)).save(event);
    }

}
