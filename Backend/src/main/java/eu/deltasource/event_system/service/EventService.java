package eu.deltasource.event_system.service;

import eu.deltasource.EventMapper;
import eu.deltasource.event_system.dto.EventViewDto;
import eu.deltasource.event_system.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
