package eu.deltasource.event_system;

import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A Spring Boot {@link CommandLineRunner} implementation that generates a large number of random {@link Event} entities
 * and saves them to the database on application startup.
 * >
 * This class is responsible for:
 * - Generating random event data (name, date, venue, capacity, etc.)
 * - Saving the events to the database using {@link EventRepository}
 * - Logging progress at intervals
 * - Measuring execution time
 */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final EventRepository eventRepository;

    public CommandLineRunnerImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Executes the event generation and database insertion when the application starts. On start of the
     * Spring Boot application this method starts creating {@link Event} objects and saving them to the database
     * and for each iteration it logs for every 10_000 {@link Event} and for every 1_000_000 objects created. It
     * also logs the time it takes the full task from start to finish.
     *
     * @param args command-line arguments (not used)
     */
    @Override
    public void run(String... args) {
        int count = 3_000; // Generate 3M events

        System.out.println("Starting process");
        long startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            Event event = generateRandomEvent();
            this.eventRepository.save(event);

            if ((i + 1) % 1_000_000 == 0) {
                System.out.println((i + 1) / 1_000_000 + " million events saved...");
            }
            if ((i + 1) % 10_000 == 0) {
                System.out.println((i+1) / 10_000 + "0 thousand events created");
            }
        }
        long endTime = System.nanoTime(); // End timing
        double elapsedTimeInSeconds = (endTime - startTime) / 1_000_000_000.0;

        System.out.println(count + " events generated and saved to the database.");
        System.out.println("Process completed in " + elapsedTimeInSeconds + " seconds.");
    }

    private Event generateRandomEvent() {
        return new Event(
                null, // Let JPA generate UUID
                getRandomEventName(),
                getRandomDateTime(),
                getRandomVenue(),
                ThreadLocalRandom.current().nextInt(5000, 50000),
                "Random Organizer",
                ThreadLocalRandom.current().nextDouble(10, 100),
                null // Attendees list is null by default
        );
    }

    private static String getRandomEventName() {
        List<String> names = Arrays.asList(
                // Original (but now completely unhinged) events
                "The International Synchronized Screaming Championship", "World Cup of Competitive Napping",
                "Extreme Underwater Basket Weaving Finals", "Annual Speed Dating for Penguins",
                "The Great Invisible Art Exhibition", "National Chicken Whispering Contest",
                "The Upside-Down Marathon", "Annual Competitive Cloud Watching",
                "Professional Level Competitive Yodeling", "The International Toilet Paper Folding Challenge",

                // Even weirder
                "The Galactic Thumb Wrestling Championship", "World’s Most Intense Rock-Paper-Scissors Battle",
                "The Annual Zombie vs. Vampire Debate Tournament", "Extreme Competitive Speed Eating… of Nothing",
                "The All-Nude Sumo Wrestling Grand Prix", "The Annual Extreme Kite Fighting World Cup",
                "International Championship of Looking Suspicious in Public", "Professional Level Whispering Contest",
                "The Fastest Speedrun of a Midlife Crisis", "The 4D Chess Tournament… with No Chessboard",

                // Absurd & totally unexplainable
                "The Intergalactic Llama Rodeo", "Olympics of Staring Contests with Fish",
                "World’s Most Awkward Hugging Championship", "The National Invisible Dog Show",
                "Extreme Level Interpretive Dance Battle Royale", "World’s Largest Collective Sneeze Attempt",
                "The Great Competitive Sarcasm Showdown", "The Annual Synchronized Sneezing Competition",
                "The Underground Mafia of Competitive Sock Folding", "International Snail Racing with Jetpacks"
        );
        return names.get(ThreadLocalRandom.current().nextInt(names.size()));
    }

    private static LocalDateTime getRandomDateTime() {
        long minDay = LocalDateTime.of(2024, 1, 1, 0, 0).toEpochSecond(java.time.ZoneOffset.UTC);
        long maxDay = LocalDateTime.of(2025, 12, 31, 23, 59).toEpochSecond(java.time.ZoneOffset.UTC);
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDateTime.ofEpochSecond(randomDay, 0, java.time.ZoneOffset.UTC);
    }

    private static String getRandomVenue() {
        List<String> venues = Arrays.asList(
                // Weird but slightly believable
                "Atlantis Stadium", "The Bouncy Castle Dome", "Random Guy’s Backyard", "Abandoned Mall Arena",
                "Upside-Down Park", "The Haunted IKEA", "Secret Lair Coliseum", "The Volcano Pit",
                "Giant Shoebox Arena", "Underground Parking Lot",

                // Getting weirder
                "The Moon Dome", "Jello Universe Stadium", "The Portal Plaza", "The Cheese Wheel Arena",
                "Goldfish Esports Hall", "Cardboard Stadium", "Floating Castle Grounds", "The Oasis Mirage",
                "Subway Fight Club", "Tornado Stadium",

                // Absolutely unhinged
                "Infinite IKEA", "Dreamland Arena", "Multiverse Battle Dome", "The Spaghetti Bowl",
                "Dystopia Coliseum", "The Sphinx’s Nose", "Orbiting Spaceship Stadium", "Shifting Location Arena",
                "Forbidden Ice Cream Pyramid", "Inside a Whale Stadium"
        );
        return venues.get(ThreadLocalRandom.current().nextInt(venues.size()));
    }
}
