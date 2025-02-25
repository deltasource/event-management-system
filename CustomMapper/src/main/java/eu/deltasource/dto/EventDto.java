package eu.deltasource.dto;

import java.util.UUID;

public class EventDto {
    private UUID id;
    private String name;
    private String dateTime;
    private String venue;
    private int maxCapacity;
    private String organizerDetails;
    private double ticketPrice;

    public EventDto() {
    }

    public EventDto(UUID id, String name, String dateTime, String venue, int maxCapacity, String organizerDetails, double ticketPrice) {
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

    public String getDateTime() {
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

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateTime(String dateTime) {
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
}
