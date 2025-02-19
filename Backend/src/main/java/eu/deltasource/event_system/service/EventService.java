package eu.deltasource.event_system.service;

import eu.deltasource.EntityMapper;
import eu.deltasource.dto.AttendeeDto;
import eu.deltasource.dto.CreateEventDto;
import eu.deltasource.dto.EventDto;
import eu.deltasource.event_system.exceptions.AttendeeNotFoundException;
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

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return eventRepository.getAll().stream()
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
        eventRepository.delete(event);
        logger.info("Event with id: {} is successfully deleted", id);
    }

    public void updateEvent(UUID id, CreateEventDto createEventDto) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
        logger.info("Founded event data: {}", event.toString());
        Event updatedEvent = entityMapper
                .mapFromTo(createEventDto, Event.class);
        logMappedEvent(updatedEvent);
        validateEvent(updatedEvent);
        logValidationSuccessful();
        updateEventData(event, updatedEvent);
        logger.info("Event with id {} has been successfully updated", id);
    }

    private void updateEventData(Event event, Event updatedEvent) {
        event.setName(updatedEvent.getName());
        event.setDateTime(updatedEvent.getDateTime());
        event.setVenue(updatedEvent.getVenue());
        event.setMaxCapacity(updatedEvent.getMaxCapacity());
        event.setOrganizerDetails(updatedEvent.getOrganizerDetails());
        event.setTicketPrice(updatedEvent.getTicketPrice());
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
        List<UUID> attendeesIds = event.getAttendees();
        return attendeesIds.stream()
                .map(attendeeId -> {
                    Attendee attendee = attendeeRepository.findById(attendeeId)
                            .orElseThrow(() -> new AttendeeNotFoundException(attendeeId));
                    return entityMapper.mapFromTo(attendee, AttendeeDto.class);
                })
                .collect(Collectors.toList());
    }

    public void addAttendeeToEvent(UUID eventId, AttendeeDto attendeeDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        Attendee attendee = entityMapper.mapFromTo(attendeeDto, Attendee.class);
        attendeeRepository.save(attendee);
        event.getAttendees().add(attendee.getId());
    }
}
