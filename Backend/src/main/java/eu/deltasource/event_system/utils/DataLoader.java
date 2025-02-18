package eu.deltasource.event_system.utils;

import eu.deltasource.EntityMapper;
import eu.deltasource.event_system.model.Attendee;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.AttendeeRepository;
import eu.deltasource.event_system.repository.EventRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * Class which is responsible for loading events, fetched
 * from a .json file, in the in-memory database
 */
@Component
public class DataLoader {
    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;
    private final EntityMapper entityMapper;

    public DataLoader(EventRepository eventRepository, AttendeeRepository attendeeRepository, EntityMapper entityMapper) {
        this.eventRepository = eventRepository;
        this.attendeeRepository = attendeeRepository;
        this.entityMapper = entityMapper;
    }

    public void loadEvents() throws IOException {
        try {
            ClassPathResource resource = new ClassPathResource("static/get-events.json");
            InputStream inputStream = resource.getInputStream();

            List<Event> fetchedEvents = entityMapper.mapToEventList(inputStream, Event.class);
            fetchedEvents.forEach(eventRepository::save);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public void loadAttendees() throws IOException {
        try {
            ClassPathResource resource = new ClassPathResource("static/get-attendees.json");
            InputStream inputStream = resource.getInputStream();

            List<Attendee> fetchedAttendees = entityMapper.mapToAttendeeList(inputStream, Attendee.class);
            fetchedAttendees.forEach(attendeeRepository::save);
        } catch (IOException e) {
            throw new IOException();
        }
    }
}
