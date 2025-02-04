package eu.deltasource.dto;

import java.time.LocalDateTime;

public record CreateEventDto(
        String name,
        LocalDateTime dateTime,
        String venue,
        int maxCapacity,
        String organizerDetails,
        double ticketPrice
) {
}
