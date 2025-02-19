package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.UUID;

public class EventGenerator {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String FILE_PATH = "src/main/resources/Events.json";

    public static void main(String[] args) {
        try {
            String jsonTemplate = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            JsonNode jsonNode = objectMapper.readTree(jsonTemplate);
            int count = 3_0; // Example: Generate 3M records

            List<Map<String, Object>> events = new ArrayList<>();

            int current = 0;
            for (int i = 0; i < count; i++) {
                events.add(generateRandomEvent(jsonNode));
                if ((i + 1) % 1_000_000 == 0) {
                    System.out.println((current += 1) + " million events generated...");
                }
            }

            saveEventsToFile(events);
            System.out.println(count + " events generated and saved to " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
        }
    }

    private static Map<String, Object> generateRandomEvent(JsonNode jsonTemplate) {
        Map<String, Object> event = new HashMap<>();

        event.put("id", UUID.randomUUID().toString());
        event.put("name", getRandomEventName());
        event.put("dateTime", getRandomDateTime());
        event.put("venue", getRandomVenue());
        event.put("category", "sport");
        event.put("ticketPrice", ThreadLocalRandom.current().nextDouble(10, 100));
        event.put("maxCapacity", ThreadLocalRandom.current().nextInt(5000, 50000));
        event.put("organizerDetails", "Random Organizer");

        return event;
    }

    private static void saveEventsToFile(List<Map<String, Object>> events) throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("events", events);
        objectMapper.writeValue(new File(FILE_PATH), jsonMap);
    }

    private static String getRandomEventName() {
        List<String> names = Arrays.asList(
                "Real Madrid - Barcelona", "Bayern Munich - Dortmund", "Chelsea - Arsenal",
                "Inter Milan - Juventus", "River Plate - Boca Juniors", "Manchester City - Liverpool",
                "PSG - Marseille", "AC Milan - Napoli", "Atletico Madrid - Sevilla", "Ajax - Feyenoord"
        );
        return names.get(ThreadLocalRandom.current().nextInt(names.size()));
    }

    private static String getRandomDateTime() {
        long minDay = new GregorianCalendar(2024, Calendar.JANUARY, 1).getTimeInMillis();
        long maxDay = new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTimeInMillis();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return new Date(randomDay).toString();
    }

    private static String getRandomVenue() {
        List<String> venues = Arrays.asList(
                "Old Trafford", "Camp Nou", "Anfield", "San Siro", "Allianz Arena", "Wembley Stadium",
                "Signal Iduna Park", "Metropolitano Stadium", "Stamford Bridge", "Maracana Stadium"
        );
        return venues.get(ThreadLocalRandom.current().nextInt(venues.size()));
    }
}
