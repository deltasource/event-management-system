import com.fasterxml.jackson.databind.ObjectMapper;
import eu.deltasource.EntityMapper;
import eu.deltasource.dto.EventDto;
import eu.deltasource.dto.EventListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class EntityMapperTest {

    @Mock
    private ObjectMapper objectMapper;
    private EntityMapper entityMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        entityMapper = new EntityMapper(objectMapper);
    }

    @Test
    public void testMapToEventList_SuccessfulMapping() throws IOException {
        //Given
        EventDto eventDto = new EventDto(UUID.randomUUID(),
                "Event 1",
                LocalDateTime.now().toString(),
                "Venue A",
                100,
                "Organizer A",
                50.0);
        InputStream inputStream = getInputStream(eventDto);
        EventListDto mockEventListDto = new EventListDto(List.of(eventDto));
        when(objectMapper.readValue(any(InputStream.class), eq(EventListDto.class)))
                .thenReturn(mockEventListDto);
        when(objectMapper.convertValue(any(), eq(EventDto.class)))
                .thenAnswer(invocation -> {
                    EventDto dto = invocation.getArgument(0);
                    return new EventDto(dto.getId(), dto.getName(), dto.getDateTime(), dto.getVenue(), dto.getMaxCapacity(), dto.getOrganizerDetails(), dto.getTicketPrice());
                });

        //When
        List<EventDto> events = entityMapper.mapToEventList(inputStream, EventDto.class);

        //Then
        assertEquals(1, events.size());
        assertEquals(eventDto.getId(), events.getFirst().getId());
        assertEquals(eventDto.getName(), events.getFirst().getName());
        assertEquals(eventDto.getDateTime(), events.getFirst().getDateTime());
        assertEquals(eventDto.getVenue(), events.getFirst().getVenue());
        assertEquals(eventDto.getMaxCapacity(), events.getFirst().getMaxCapacity());
        assertEquals(eventDto.getOrganizerDetails(), events.getFirst().getOrganizerDetails());
        assertEquals(eventDto.getTicketPrice(), events.getFirst().getTicketPrice());
    }

    @Test
    public void testMapToEventList_whenJsonNotValid_ExceptionThrown() throws IOException {
        //Given
        String json = "{ \"events\": [ {\"name\": \"Event 1\" } ] }";
        InputStream inputStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        when(objectMapper.readValue(inputStream, EventListDto.class))
                .thenThrow(new IOException());

        //When, Then
        assertThrows(IOException.class, () -> entityMapper.mapToEventList(inputStream, EventDto.class));
    }

    private static InputStream getInputStream(EventDto dto) {
        String json = "{ \"events\": [ { " +
                "\"id\": \"" + dto.getId() + "\", " +
                "\"name\": \"" + dto.getName() + "\", " +
                "\"dateTime\": \"" + dto.getDateTime() + "\", " +
                "\"venue\": \"" + dto.getVenue() + "\", " +
                "\"maxCapacity\": " + dto.getMaxCapacity() + ", " +
                "\"organizerDetails\": \"" + dto.getOrganizerDetails() + "\", " +
                "\"ticketPrice\": " + dto.getTicketPrice() + " " +
                "} ] }";
        return new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
    }
}
