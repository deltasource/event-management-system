package eu.deltasource.event_system.controller;

import eu.deltasource.dto.CreateEventDto;
import eu.deltasource.dto.EventDto;
import eu.deltasource.event_system.exceptions.EventNotFoundException;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import eu.deltasource.event_system.service.EventService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private EventService eventService;
    @Mock
    private EventRepository eventRepository;

    @Test
    public void getAllEvents_shouldReturnSuccess() throws Exception {
        //Given
        EventDto eventDto = new EventDto(UUID.randomUUID(), "Event 1","2025-02-01T20:00","Stadium A" , 50, "Organizer",50);
        List<EventDto> mockEvents = List.of(eventDto);
        when(eventService.getAllEvents()).thenReturn(mockEvents);

        //When, Then
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Event 1"))
                .andExpect(jsonPath("$[0].dateTime").value("2025-02-01T20:00"))
                .andExpect(jsonPath("$[0].venue").value("Stadium A"))
                .andExpect(jsonPath("$[0].ticketPrice").value("50.0"));
    }

    @Test
    public void create_whenEverythingIsValid() throws Exception {
        //Given
        CreateEventDto createEventDto = new CreateEventDto(
                "Concert A",
                LocalDateTime.now().toString(),
                "Stadium A",
                500,
                "Music Corp.",
                25.5
        );
        when(eventService.create(any(CreateEventDto.class)))
                .thenReturn(createEventDto.name());

        //When, Then
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Concert A",
                                  "dateTime": "2025-02-01T20:00:00",
                                  "venue": "Stadium A",
                                  "maxCapacity": 500,
                                  "organizerDetails": "Music Corp.",
                                  "ticketPrice": 25.5
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully added event: Concert A"));
    }

    @Test
    public void create_whenDataIsNotValid_returnBadRequest() throws Exception {
        //Given
        String invalidEventJson = """
                {
                  "dateTime": "2025-02-01T20:00:00",
                  "venue": "Stadium A",
                  "maxCapacity": 500,
                  "organizerDetails": "Music Corp.",
                  "ticketPrice": 25.5
                }
                """;
        when(eventService.create(any(CreateEventDto.class)))
                .thenThrow(new ValidationException("Event name cannot be null."));

        //When, Then
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidEventJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Event name cannot be null."));
    }

    @Test
    public void deleteEvent_whenIsFound() throws Exception {
        //Given
        UUID uuid = UUID.randomUUID();
        Event event = new Event(uuid, "Event", LocalDateTime.now(), "venue", 100, "details", 10);
        when(eventRepository.findById(uuid))
                .thenReturn(Optional.of(event));

        //When
        mockMvc.perform(delete("/events/{id}", uuid))
                .andExpect(status().isOk())
                .andExpect(content().string("The event is successfully deleted!"));
    }

    @Test
    public void deleteEvent_whenIsNotFound_thenStatusIsNotFound() throws Exception {
        //Given
        UUID uuid = UUID.randomUUID();
        doThrow(new EventNotFoundException(uuid)).when(eventService).delete(uuid);

        //When
        mockMvc.perform(delete("/events/{id}", uuid))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Event with id " + uuid + " is not found"));
    }

    @Test
    public void update_whenEverythingIsValid() throws Exception {
        //Given
        UUID uuid = UUID.randomUUID();
        doNothing().when(eventService).updateEvent(eq(uuid), any(CreateEventDto.class));

        //When, Then
        mockMvc.perform(put("/events/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "  \"name\": \"Updated Concert\"," +
                                "  \"dateTime\": \"2025-03-01T20:00:00\"," +
                                "  \"venue\": \"Updated Stadium\"," +
                                "  \"maxCapacity\": 600," +
                                "  \"organizerDetails\": \"Updated Music Corp.\"," +
                                "  \"ticketPrice\": 30.0" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string("The event is successfully updated!"));
    }

    @Test
    public void update_whenDataIsNotValid_returnBadRequest() throws Exception {
        //Given
        UUID uuid = UUID.randomUUID();
        String invalidEventJson = """
                {
                  "dateTime": "2025-02-01T20:00:00",
                  "venue": "Stadium A",
                  "maxCapacity": 500,
                  "organizerDetails": "Music Corp.",
                  "ticketPrice": 25.5
                }
                """;
        doThrow(new ValidationException("Event name cannot be null."))
                .when(eventService).updateEvent(eq(uuid), any(CreateEventDto.class));

        //When, Then
        mockMvc.perform(put("/events/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidEventJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Event name cannot be null."));
    }
}
