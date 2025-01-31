package eu.deltasource.event_system;

import eu.deltasource.event_system.controller.EventController;
import eu.deltasource.event_system.dto.AddEventDto;
import eu.deltasource.event_system.dto.EventViewDto;
import eu.deltasource.event_system.exceptions.EventNotFoundException;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import eu.deltasource.event_system.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

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
    @Mock
    private BindingResult bindingResult;
    @MockitoBean
    private EventService eventService;
    @Mock
    private EventRepository eventRepository;

    @Test
    public void testGetAllEvents() throws Exception {
        //Given
        EventViewDto eventViewDto1 = new EventViewDto("Event 1", "Stadium A", "2025-02-01T20:00", 50.0);
        List<EventViewDto> mockEvents = List.of(eventViewDto1);
        when(eventService.getAllEvents()).thenReturn(mockEvents);

        //When, Then
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Event 1"))
                .andExpect(jsonPath("$[0].venue").value("Stadium A"))
                .andExpect(jsonPath("$[0].dateTime").value("2025-02-01T20:00"))
                .andExpect(jsonPath("$[0].ticketPrice").value("50.0"));
    }

    @Test
    public void addEvent_whenEverythingIsValid() throws Exception {
        //Given
        AddEventDto addEventDto = new AddEventDto(
                "Concert A",
                LocalDateTime.now(),
                "Stadium A",
                500,
                "Music Corp.",
                25.5
        );
        when(eventService.add(any(AddEventDto.class)))
                .thenReturn("Concert A");

        //When, Then
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "  \"name\": \"Concert A\"," +
                                "  \"dateTime\": \"2025-02-01T20:00:00\"," +
                                "  \"venue\": \"Stadium A\"," +
                                "  \"maxCapacity\": 500," +
                                "  \"organizerDetails\": \"Music Corp.\"," +
                                "  \"ticketPrice\": 25.5" +
                                "}\n"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully added event: Concert A"));
    }

    @Test
    public void addEvent_whenDataIsNotValid_returnBadRequest() throws Exception {
        // Given
        String invalidEventJson = "{" +
                "\"dateTime\": \"2025-02-01T20:00:00\"," +
                "\"venue\": \"Stadium A\"," +
                "\"maxCapacity\": 500," +
                "\"organizerDetails\": \"Music Corp.\"," +
                "\"ticketPrice\": 25.5" +
                "}";

        // When, Then
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidEventJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Event name cannot be null. "));
    }

    @Test
    public void deleteEvent_whenEventIsFound() throws Exception {
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
    public void deleteEvent_whenEventIsNotFound_thenStatusIsNotFound() throws Exception {
        //Given
        UUID uuid = UUID.randomUUID();
        doThrow(new EventNotFoundException("This event is not found")).when(eventService).delete(uuid);

        //When
        mockMvc.perform(delete("/events/{id}", uuid))
                .andExpect(status().isNotFound())
                .andExpect(content().string("This event is not found"));
    }

    @Test
    public void updateEvent_whenEverythingIsValid() throws Exception {
        // Given
        UUID uuid = UUID.randomUUID();
        AddEventDto updateEvent = new AddEventDto(
                "Updated Concert",
                LocalDateTime.now(),
                "Updated Stadium",
                600,
                "Updated Music Corp.",
                30.0
        );

        doNothing().when(eventService).updateEvent(eq(uuid), any(AddEventDto.class));

        // When, Then
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
    public void updateEvent_whenDataIsNotValid_returnBadRequest() throws Exception {
        // Given
        UUID uuid = UUID.randomUUID();
        AddEventDto updateEvent = new AddEventDto(
                "Updated Concert",
                LocalDateTime.now(),
                "Updated Stadium",
                600,
                "Updated Music Corp.",
                30.0
        );

        doNothing().when(eventService).updateEvent(eq(uuid), any(AddEventDto.class));

        // When, Then
        mockMvc.perform(put("/events/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "  \"dateTime\": \"2025-03-01T20:00:00\"," +
                                "  \"venue\": \"Updated Stadium\"," +
                                "  \"maxCapacity\": 600," +
                                "  \"organizerDetails\": \"Updated Music Corp.\"," +
                                "  \"ticketPrice\": 30.0" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Event name cannot be null. "));
    }
}
