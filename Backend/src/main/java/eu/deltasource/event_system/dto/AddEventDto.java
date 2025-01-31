package eu.deltasource.event_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;


public record AddEventDto(

        @NotNull(message = "Event name cannot be null.")
        @Size(min = 2, max = 40, message = "Event name must be between 2 and 40 characters.")
        String name,

        @NotNull(message = "Event date and time cannot be null.")
        LocalDateTime dateTime,

        @NotNull(message = "Venue cannot be null.")
        @Size(min = 2, max = 40, message = "Venue name must be between 2 and 40 characters.")
        String venue,

        @NotNull(message = "Maximum capacity cannot be null.")
        @Positive(message = "Maximum capacity must be a positive number.")
        int maxCapacity,

        @NotNull(message = "Organizer details cannot be null.")
        @Size(min = 2, max = 40, message = "Organizer details must be between 2 and 40 characters.")
        String organizerDetails,

        @NotNull(message = "Ticket price cannot be null.")
        @Positive(message = "Ticket price must be a positive value.")
        double ticketPrice
) {
}


