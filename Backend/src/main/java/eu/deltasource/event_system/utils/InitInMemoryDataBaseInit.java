package eu.deltasource.event_system.repository;

import eu.deltasource.event_system.model.Event;

import java.time.LocalDateTime;

public class InitInMemoryDataBaseInit {
    private final EventRepository eventRepository;

    public InitInMemoryDataBaseInit(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void initInMemoryEvents(){
        eventRepository.save(new Event(1L, "Event1", LocalDateTime.now().plusDays(5), 12));
        eventRepository.save(new Event(2L, "Event2", LocalDateTime.now().plusDays(15), 20));
        eventRepository.save(new Event(3L, "Event3", LocalDateTime.now().plusDays(20), 32));
    }
}
