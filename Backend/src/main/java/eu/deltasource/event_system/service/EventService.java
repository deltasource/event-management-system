package eu.deltasource.event_system.service;

import eu.deltasource.EntityMapper;
import eu.deltasource.dto.AttendeeDto;
import eu.deltasource.dto.CreateEventDto;
import eu.deltasource.dto.EventDto;
import eu.deltasource.event_system.exceptions.EventNotFoundException;
import eu.deltasource.event_system.model.Attendee;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.AttendeeRepository;
import eu.deltasource.event_system.repository.EventRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Service class, responsible for the business logic connected
 * with the events
 */
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EntityMapper entityMapper;
    private final Validator validator;
    private final Logger logger = LoggerFactory.getLogger(EventService.class);
    private final AttendeeRepository attendeeRepository;

    public EventService(EventRepository eventRepository, EntityMapper entityMapper
            , Validator validator, AttendeeRepository attendeeRepository) {
        this.eventRepository = eventRepository;
        this.entityMapper
                = entityMapper
        ;
        this.validator = validator;
        this.attendeeRepository = attendeeRepository;
    }

    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(e -> entityMapper
                        .mapFromTo(e, EventDto.class))
                .toList();
    }

    public String create(CreateEventDto createEventDto) {
        Event event = entityMapper
                .mapFromTo(createEventDto, Event.class);
        logMappedEvent(event);
        validateEvent(event);
        logValidationSuccessful();
        eventRepository.save(event);
        logger.info("The event is successfully saved in the repository");
        return event.getName();
    }

    public void delete(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
        logger.info("Event with id: {}  is founded", id);
        attendeeRepository.deleteAll(event.getAttendees());
        eventRepository.delete(event);
        logger.info("Event with id: {} is successfully deleted", id);
    }

    public void updateEvent(UUID id, CreateEventDto createEventDto) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
        logger.info("Found event data: {}", event);
        updateEvent(createEventDto, event);
        validateEvent(event);
        logger.info("Validation successful for event id: {}", id);
        eventRepository.save(event);
        logger.info("Event with id {} has been successfully updated", id);
    }

    private static void updateEvent(CreateEventDto createEventDto, Event event) {
        event.setName(createEventDto.name());
        event.setDateTime(LocalDateTime.parse(createEventDto.dateTime()));
        event.setVenue(createEventDto.venue());
        event.setMaxCapacity(createEventDto.maxCapacity());
        event.setOrganizerDetails(createEventDto.organizerDetails());
        event.setTicketPrice(createEventDto.ticketPrice());
    }

    private void validateEvent(Event event) {
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<Event> violation : violations) {
                stringBuilder.append(violation.getMessage()).append(" ");
            }
            throw new ValidationException(stringBuilder.toString());
        }
    }

    private void logMappedEvent(Event event) {
        logger.info("Mapped event data: {}", event.toString());
    }

    private void logValidationSuccessful() {
        logger.info("Validation is successful");
    }

    public List<AttendeeDto> getAllByEvent(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
        List<Attendee> attendees = event.getAttendees();
        return attendees.stream()
                .map(attendee -> entityMapper.mapFromTo(attendee, AttendeeDto.class)
                ).toList();
    }

    public void addAttendeeToEvent(UUID eventId, AttendeeDto attendeeDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        Attendee attendee = entityMapper.mapFromTo(attendeeDto, Attendee.class);
        attendee.setEvent(event);
        attendeeRepository.save(attendee);
        event.getAttendees().add(attendee);
        eventRepository.save(event);
    }
}
