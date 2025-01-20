package eu.deltasource.event_system.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.deltasource.event_system.repository.EventRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Class which is responsible for loading events, fetched
 * from a .json file, in the in-memory database
 */
@Component
public class EventDataLoader {
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    public EventDataLoader(EventRepository eventRepository, ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
    }

    public void loadEvents() throws IOException {
        try {
            File jsonFile = new File("Backend/src/main/resources/static/get-events.json");
            EventListWrapper wrapper = objectMapper.readValue(jsonFile, EventListWrapper.class);
            wrapper.getEvents().forEach(eventRepository::save);
        } catch (IOException e) {
            throw new IOException();
        }
    }
}
