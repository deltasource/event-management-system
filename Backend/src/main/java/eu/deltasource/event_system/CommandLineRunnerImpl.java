package eu.deltasource.event_system;

import eu.deltasource.event_system.utils.EventDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final EventDataLoader eventDataLoader;

    public CommandLineRunnerImpl(EventDataLoader eventDataLoader) {
        this.eventDataLoader = eventDataLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        eventDataLoader.loadEvents();
    }
}
