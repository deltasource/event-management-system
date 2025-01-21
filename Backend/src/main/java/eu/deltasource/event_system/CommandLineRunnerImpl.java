package eu.deltasource.event_system;

import eu.deltasource.event_system.service.EventService;
import eu.deltasource.event_system.utils.EventDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final EventDataLoader eventDataLoader;
    private final EventService eventService;

    public CommandLineRunnerImpl(EventDataLoader eventDataLoader, EventService eventService) {
        this.eventDataLoader = eventDataLoader;
        this.eventService = eventService;
    }

    @Override
    public void run(String... args) throws Exception {
        eventDataLoader.loadEvents();
    }
}
