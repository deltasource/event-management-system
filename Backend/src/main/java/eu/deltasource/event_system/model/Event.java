package eu.deltasource.event_system.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Event {
    private UUID id;
    private String name;
    private LocalDateTime dateTime;
    double ticketPrice;

    public Event(UUID id, String name, LocalDateTime dateTime, double ticketPrice) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.ticketPrice = ticketPrice;
    }

    public UUID getId() {
        return id;
    }
}
