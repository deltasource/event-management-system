package eu.deltasource.dto;


public record CreateEventDto(
        String name,
        String dateTime,
        String venue,
        int maxCapacity,
        String organizerDetails,
        double ticketPrice
) {
}
