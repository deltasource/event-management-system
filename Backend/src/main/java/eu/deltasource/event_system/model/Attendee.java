package eu.deltasource.event_system.model;

import eu.deltasource.event_system.model.enums.TicketType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class Attendee {
    private UUID id;
    @NotNull(message = "First name cannot be null.")
    @Size(min = 2, max = 40, message = "First name must be between 2 and 40 characters.")
    private String firstName;
    @NotNull(message = "Last name cannot be null.")
    @Size(min = 2, max = 40, message = "Last name must be between 2 and 40 characters.")
    private String lastName;
    @NotNull(message = "Email cannot be null.")
    @Email
    private String email;
    @NotNull(message = "Ticket type cannot be null")
    private TicketType ticketType;
    @NotNull(message = "Event id cannot be null.")
    private UUID event;

    public Attendee(String firstName, String lastName, String email, UUID event) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.event = event;
    }
}
