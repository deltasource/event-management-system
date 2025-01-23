package eu.deltasource.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDto(UUID id,
                       String name,
                       LocalDateTime dateTime,
                       String venue,
                       int maxCapacity,
                       String organizerDetails,
                       double ticketPrice) {
}
