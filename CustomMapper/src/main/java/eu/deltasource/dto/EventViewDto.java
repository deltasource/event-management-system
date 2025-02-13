package eu.deltasource.dto;

import java.util.UUID;

public record EventViewDto(UUID id, String name, String venue, String dateTime, double ticketPrice, int maxCapacity, String organizerDetails) {
}
