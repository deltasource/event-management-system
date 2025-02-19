package eu.deltasource.event_system;

import eu.deltasource.event_system.utils.DataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final DataLoader dataLoader;

    public CommandLineRunnerImpl(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        dataLoader.loadEvents();
    }
}
