package eu.deltasource.event_system.utils;

import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class InitInMemoryDataBaseInit {
    private final EventRepository eventRepository;

    public InitInMemoryDataBaseInit(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void initInMemoryEvents(){
        LocalDateTime futureEventDate = LocalDateTime.now().plusDays(5);
        eventRepository.save(new Event(UUID.randomUUID(), "Event1", LocalDateTime.now().plusDays(5), 12));
        eventRepository.save(new Event(UUID.randomUUID(), "Event2", LocalDateTime.now().plusDays(15), 20));
        eventRepository.save(new Event(UUID.randomUUID(), "Event3", LocalDateTime.now().plusDays(20), 32));
    }
}
