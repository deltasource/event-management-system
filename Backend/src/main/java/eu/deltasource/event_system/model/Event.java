package eu.deltasource.event_system.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public class Event {

    private UUID id;
    @NotNull(message = "Event name cannot be null.")
    @Size(min = 2, max = 40, message = "Event name must be between 2 and 40 characters.")
    private String name;
    @NotNull(message = "Event date and time cannot be null.")
    private LocalDateTime dateTime;
    @NotNull(message = "Venue cannot be null.")
    @Size(min = 2, max = 40, message = "Venue name must be between 2 and 40 characters.")
    private String venue;
    @NotNull(message = "Maximum capacity cannot be null.")
    @Positive(message = "Maximum capacity must be a positive number.")
    private int maxCapacity;
    @NotNull(message = "Organizer details cannot be null.")
    @Size(min = 2, max = 40, message = "Organizer details must be between 2 and 40 characters.")
    private String organizerDetails;
    @NotNull(message = "Ticket price cannot be null.")
    @Positive(message = "Ticket price must be a positive value.")
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
