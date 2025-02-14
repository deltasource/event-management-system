package eu.deltasource.dto;

import java.util.UUID;

public record EventDto(UUID id,
                       String name,
                       String dateTime,
                       String venue,
                       int maxCapacity,
                       String organizerDetails,
                       double ticketPrice) {
}
