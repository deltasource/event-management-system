package eu.deltasource.event_system.service;

import eu.deltasource.EventMapper;
import eu.deltasource.event_system.dto.AddEventDto;
import eu.deltasource.event_system.dto.EventViewDto;
import eu.deltasource.event_system.exceptions.EventNotFoundException;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service class, responsible for the business logic connected
 * with the events
 */
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public List<EventViewDto> getAllEvents() {
        return eventRepository.getAll().stream()
                .map(e -> eventMapper.mapFromTo(e, EventViewDto.class))
                .toList();
    }

    public String add(AddEventDto eventDto) {
        Event event = eventMapper.mapFromTo(eventDto, Event.class);
        eventRepository.save(event);
        return event.getName();
    }

    public void delete(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("This event is not found"));
        eventRepository.delete(event);
    }

    public void updateEvent(UUID id, AddEventDto addEventDto) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("This event is not found"));
        updateEventData(event, addEventDto);
    }

    private void updateEventData(Event event, AddEventDto addEventDto) {
        event.setName(addEventDto.name());
        event.setDateTime(addEventDto.dateTime());
        event.setVenue(addEventDto.venue());
        event.setMaxCapacity(addEventDto.maxCapacity());
        event.setOrganizerDetails(addEventDto.organizerDetails());
        event.setTicketPrice(addEventDto.ticketPrice());
    }
}
