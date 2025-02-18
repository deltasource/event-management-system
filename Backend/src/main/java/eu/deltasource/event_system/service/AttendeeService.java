package eu.deltasource.event_system.service;

import eu.deltasource.event_system.exceptions.EventNotFoundException;
import eu.deltasource.event_system.model.Attendee;
import eu.deltasource.event_system.repository.AttendeeRepository;
import eu.deltasource.event_system.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final EventRepository eventRepository;

    public AttendeeService(AttendeeRepository attendeeRepository, EventRepository eventRepository) {
        this.attendeeRepository = attendeeRepository;
        this.eventRepository = eventRepository;
    }

    public List<Attendee> getAllByEvent(UUID id) {
        if (eventRepository.findById(id).isEmpty()) {
            throw new EventNotFoundException(id);
        }
        return attendeeRepository.getAll().stream()
                .filter(a -> a.getEvent() != null && a.getEvent().equals(id))
                .toList();
    }
}
