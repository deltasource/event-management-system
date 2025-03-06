package eu.deltasource.event_system.service;

import eu.deltasource.dto.AttendeeDto;
import eu.deltasource.dto.CreateEventDto;
import eu.deltasource.dto.EventDto;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class EventServiceTest {

    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;

    @ServiceConnection
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14");

    @Test
    void connectionEstablished() {
        assertTrue(postgreSQLContainer.isCreated());
        assertTrue(postgreSQLContainer.isRunning());
    }

    @Test
    @Transactional
    void getAllEvents_Successfully() {
        //When
        List<EventDto> fetchedEvents = eventService.getAllEvents();

        //Then
        assertFalse(fetchedEvents.isEmpty());
    }

    @Test
    void createEvent_Successfully() {
        //Given
        CreateEventDto createEventDto = new CreateEventDto("Event", LocalDateTime.now().toString(), "venue", 100, "details", 10);

        //When
        String eventName = eventService.create(createEventDto);

        //Then
        assertEquals(createEventDto.name(), eventName);
        Optional<Event> optionalEvent = eventRepository.findAll().stream()
                .filter(e -> e.getName().equals("Event"))
                .findFirst();
        if (optionalEvent.isPresent()) {
            Event savedEvent = optionalEvent.get();
            assertEquals(savedEvent.getName(), createEventDto.name());
            assertEquals(savedEvent.getDateTime().toString(), createEventDto.dateTime());
            assertEquals(savedEvent.getVenue(), createEventDto.venue());
            assertEquals(savedEvent.getMaxCapacity(), createEventDto.maxCapacity());
            assertEquals(savedEvent.getOrganizerDetails(), createEventDto.organizerDetails());
            assertEquals(savedEvent.getTicketPrice(), createEventDto.ticketPrice());
        }
    }

    @Test
    @Transactional
    void deleteEvent_Successfully() {
        //Given
        CreateEventDto createEventDto = new CreateEventDto("Event", LocalDateTime.now().toString(), "venue", 100, "details", 10);
        eventService.create(createEventDto);
        Optional<Event> optionalEvent = eventRepository.findAll().stream()
                .filter(e -> e.getName().equals("Event"))
                .findFirst();
        UUID eventId = null;
        if (optionalEvent.isPresent()) {
            eventId = optionalEvent.get().getId();
        }

        //When
        eventService.delete(eventId);

        //Then
        assertNotNull(eventId);
        assertTrue(eventRepository.findById(eventId).isEmpty());
    }

    @Test
    void updateEvent_Successfully() {
        //Given
        CreateEventDto createEventDto = new CreateEventDto("Event", LocalDateTime.now().toString(), "venue", 100, "details", 10);
        CreateEventDto updateDto = new CreateEventDto("UpdatedEvent", LocalDateTime.now().toString(), "venue", 100, "details", 10);
        eventService.create(createEventDto);
        Optional<Event> optionalEvent = eventRepository.findAll().stream()
                .filter(e -> e.getName().equals("Event"))
                .findFirst();
        UUID eventId = null;
        if (optionalEvent.isPresent()) {
            eventId = optionalEvent.get().getId();
        }

        //When
        eventService.updateEvent(eventId, updateDto);

        //Then
        assertNotNull(eventId);
        assertFalse(eventRepository.findById(eventId).isEmpty());
        assertEquals(updateDto.name(), eventRepository.findById(eventId).get().getName());
    }

    @Test
    @Transactional
    void getAttendeesOfEvent_Successfully() {
        //Given
        CreateEventDto createEventDto = new CreateEventDto("Event", LocalDateTime.now().toString(), "venue", 100, "details", 10);
        eventService.create(createEventDto);
        Optional<Event> optionalEvent = eventRepository.findAll().stream()
                .filter(e -> e.getName().equals("Event"))
                .findFirst();
        UUID eventId = null;
        if (optionalEvent.isPresent()) {
            eventId = optionalEvent.get().getId();
        }
        AttendeeDto attendeeToAdd = new AttendeeDto(null, "John", "Smith", "john@gmail.com", "VIP");
        eventService.addAttendeeToEvent(eventId, attendeeToAdd);

        //When
        List<AttendeeDto> attendees = eventService.getAllByEvent(eventId);

        //Then
        AttendeeDto fetchedAttendee = attendees.get(0);
        assertNotNull(eventId);
        assertNotNull(attendees);
        assertEquals(1, attendees.size());
        assertEquals(attendeeToAdd.firstName(), fetchedAttendee.firstName());
        assertEquals(attendeeToAdd.lastName(), fetchedAttendee.lastName());
        assertEquals(attendeeToAdd.email(), fetchedAttendee.email());
        assertEquals(attendeeToAdd.ticketType(), fetchedAttendee.ticketType());
    }
}
