package eu.deltasource.event_system.service;

import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        List<Event> allEvents = eventRepository.getAll();
        return allEvents;
    }
}
