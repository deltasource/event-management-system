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

    public Attendee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}
