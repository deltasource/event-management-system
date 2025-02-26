package eu.deltasource.event_system.service;

import eu.deltasource.EntityMapper;
import eu.deltasource.dto.AttendeeDto;
import eu.deltasource.dto.CreateEventDto;
import eu.deltasource.dto.EventDto;
import eu.deltasource.event_system.exceptions.EventMappingException;
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

import java.io.IOException;
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
        this.entityMapper = entityMapper;
        this.validator = validator;
        this.attendeeRepository = attendeeRepository;
    }

    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(e -> {
                    EventDto eventDto = entityMapper.mapFromTo(e, EventDto.class);
                    eventDto.setTicketPrice(formatPrice(eventDto));
                    return eventDto;
                })
                .toList();
    }

    public String create(CreateEventDto createEventDto) {
        Event event = entityMapper
                .mapFromTo(createEventDto, Event.class);
        logger.info("Mapped event: {}", event);
        validateEvent(event);
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
        try {
            event = entityMapper.mapFromTo(createEventDto, event);
        } catch (IOException e) {
            throw new EventMappingException();
        }
        validateEvent(event);
        logger.info("Validation successful for event id: {}", id);
        eventRepository.save(event);
        logger.info("Event with id {} has been successfully updated", id);
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

    public List<AttendeeDto> getAllByEvent(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
        List<Attendee> attendees = event.getAttendees();
        return attendees.stream()
                .map(attendee -> entityMapper.mapFromTo(attendee, AttendeeDto.class)
                ).toList();
    }

    public void addAttendeeToEvent(UUID eventId, AttendeeDto attendeeDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        logger.info("Attempting to add attendee to event with ID: {}", eventId);
        Attendee attendee = entityMapper.mapFromTo(attendeeDto, Attendee.class);
        logger.debug("Mapped AttendeeDto to Attendee entity: {}", attendee);
        attendee.setEvent(event);
        attendeeRepository.save(attendee);
        logger.info("Attendee with ID: {} has been added to the database", attendee.getId());
        event.getAttendees().add(attendee);
        eventRepository.save(event);
        logger.info("Event with ID: {} has been updated with the new attendee", eventId);
    }

    private static double formatPrice(EventDto eventDto) {
        return Math.round(eventDto.getTicketPrice() * 100) / 100.0;
    }
}
