package eu.deltasource.event_system.utils;

import eu.deltasource.EntityMapper;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Class which is responsible for loading events, fetched
 * from a .json file, in the in-memory database
 */
@Component
public class DataLoader {
    private final EventRepository eventRepository;
    private final EntityMapper entityMapper;

    public DataLoader(EventRepository eventRepository, EntityMapper entityMapper) {
        this.eventRepository = eventRepository;
        this.entityMapper = entityMapper;
    }

    public void loadEvents() throws IOException {
        try {
            ClassPathResource resource = new ClassPathResource("static/get-events.json");
            InputStream inputStream = resource.getInputStream();

            List<Event> fetchedEvents = entityMapper.mapToEventList(inputStream, Event.class);
            fetchedEvents.forEach(
                    e -> e.setAttendees(new ArrayList<>())
            );
            fetchedEvents.forEach(eventRepository::save);
        } catch (IOException e) {
            throw new IOException();
        }
    }
}
