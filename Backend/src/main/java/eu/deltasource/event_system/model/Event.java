package eu.deltasource.event_system.model;


import java.time.LocalDateTime;
import java.util.UUID;

public class Event {
    private UUID id;
    private String name;
    private LocalDateTime dateTime;
    private String venue;
    private int maxCapacity;
    private String organizerDetails;
    private double ticketPrice;

    public Event() {
    }

    public Event(UUID id, String name, LocalDateTime dateTime, String venue, int maxCapacity, String organizerDetails, double ticketPrice) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.venue = venue;
        this.maxCapacity = maxCapacity;
        this.organizerDetails = organizerDetails;
        this.ticketPrice = ticketPrice;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getVenue() {
        return venue;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getOrganizerDetails() {
        return organizerDetails;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setOrganizerDetails(String organizerDetails) {
        this.organizerDetails = organizerDetails;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
