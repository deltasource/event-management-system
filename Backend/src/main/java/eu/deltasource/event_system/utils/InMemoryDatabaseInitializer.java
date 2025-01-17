package eu.deltasource.event_system.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.deltasource.event_system.repository.EventRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Class which is responsible for initializing the in-memory database
 * after the project is started
 */
@Component
public class InMemoryDatabaseInitializer {
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    public InMemoryDatabaseInitializer(EventRepository eventRepository, ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
    }

    public void initInMemoryEvents() throws IOException {
        try {
            File jsonFile = new File("Backend/src/main/resources/static/get-events.json");
            EventListWrapper wrapper = objectMapper.readValue(jsonFile, EventListWrapper.class);
            wrapper.getEvents().forEach(eventRepository::save);
        } catch (IOException e) {
            throw new IOException();
        }
    }
}
