package eu.deltasource.dto;

import java.util.UUID;

public record AttendeeDto(UUID id,
                          String firstName,
                          String lastName,
                          String email,
                          String ticketType
) {
}
