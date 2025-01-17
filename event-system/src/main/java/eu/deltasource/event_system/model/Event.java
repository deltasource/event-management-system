package eu.deltasource.event_system.model;

import java.time.LocalDateTime;

public class Event {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    double ticketPrice;

    public Event(Long id, String name, LocalDateTime dateTime, double ticketPrice) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.ticketPrice = ticketPrice;
    }
}
