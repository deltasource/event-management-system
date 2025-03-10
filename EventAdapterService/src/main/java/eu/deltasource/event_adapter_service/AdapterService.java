package eu.deltasource.event_adapter_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AdapterService {

    public List<CreateEventDto> handleResponse(String responseBody) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode events = rootNode.get("events");
            List<CreateEventDto> mappedEvents = new ArrayList<>();
            for (int i = 0; i < events.size(); i++){
                FetchedEvent fetchedEvent = objectMapper.treeToValue(events.get(i), FetchedEvent.class);
                CreateEventDto createEventDto = mapFetchedEvent(fetchedEvent);
                mappedEvents.add(createEventDto);
            }
        return mappedEvents;
    }

    private static CreateEventDto mapFetchedEvent(FetchedEvent fetchedEvent) throws JsonProcessingException {
        Integer fetchedCapacity = fetchedEvent.getCapacity();
        int capacity = fetchedCapacity == null ? 100 : fetchedCapacity;
        return new CreateEventDto(fetchedEvent.getName().getText(), fetchedEvent.getStart().getLocal(), getRandomVenue(), capacity, "unknown", ThreadLocalRandom.current().nextDouble(10, 100));
    }

    private static String getRandomVenue() {
        List<String> venues = Arrays.asList(
                "Atlantis Stadium", "The Bouncy Castle Dome", "Random Guy’s Backyard", "Abandoned Mall Arena",
                "Upside-Down Park", "The Haunted IKEA", "Secret Lair Coliseum", "The Volcano Pit",
                "Giant Shoebox Arena", "Underground Parking Lot",
                "The Moon Dome", "Jello Universe Stadium", "The Portal Plaza", "The Cheese Wheel Arena",
                "Goldfish Esports Hall", "Cardboard Stadium", "Floating Castle Grounds", "The Oasis Mirage",
                "Subway Fight Club", "Tornado Stadium",
                "Infinite IKEA", "Dreamland Arena", "Multiverse Battle Dome", "The Spaghetti Bowl",
                "Dystopia Coliseum", "The Sphinx’s Nose", "Orbiting Spaceship Stadium", "Shifting Location Arena",
                "Forbidden Ice Cream Pyramid", "Inside a Whale Stadium"
        );
        return venues.get(ThreadLocalRandom.current().nextInt(venues.size()));
    }
}
