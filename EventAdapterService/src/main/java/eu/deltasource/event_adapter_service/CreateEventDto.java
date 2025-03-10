package eu.deltasource.event_adapter_service;

public record CreateEventDto(String name, String dateTime, String venue, int maxCapacity, String organizerDetails, double ticketPrice) {
    public CreateEventDto(String name, String dateTime, String venue, int maxCapacity, String organizerDetails, double ticketPrice) {
        this.name = name;
        this.dateTime = dateTime;
        this.venue = venue;
        this.maxCapacity = maxCapacity;
        this.organizerDetails = organizerDetails;
        this.ticketPrice = ticketPrice;
    }
}