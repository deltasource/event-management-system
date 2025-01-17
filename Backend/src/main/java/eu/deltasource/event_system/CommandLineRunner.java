package eu.deltasource.event_system;

import eu.deltasource.event_system.utils.InMemoryDatabaseInitializer;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {
    private final InMemoryDatabaseInitializer inMemoryDatabaseInitializer;

    public CommandLineRunner(InMemoryDatabaseInitializer inMemoryDatabaseInitializer) {
        this.inMemoryDatabaseInitializer = inMemoryDatabaseInitializer;
    }

    @Override
    public void run(String... args) throws Exception {
        inMemoryDatabaseInitializer.initInMemoryEvents();
    }
}
