package eu.deltasource.event_system.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.deltasource.event_system.dto.EventListDto;
import eu.deltasource.event_system.repository.EventRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

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
            ClassPathResource resource = new ClassPathResource("static/get-events.json");
            InputStream inputStream = resource.getInputStream();

            EventListDto fetchedEvents = objectMapper.readValue(inputStream, EventListDto.class);
            fetchedEvents.events().forEach(eventRepository::save);
        } catch (IOException e) {
            throw new IOException();
        }
    }
}
