import com.fasterxml.jackson.databind.ObjectMapper;
import eu.deltasource.EventMapper;
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

public class EventMapperTest {

    @Mock
    private ObjectMapper objectMapper;

    private EventMapper eventMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        eventMapper = new EventMapper(objectMapper);
    }

    @Test
    public void testMapToEventList_SuccessfulMapping() throws IOException {
        //Given
        EventDto eventDto = new EventDto(UUID.randomUUID(),
                "Event 1",
                LocalDateTime.now(),
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
                    return new EventDto(dto.id(), dto.name(), dto.dateTime(), dto.venue(), dto.maxCapacity(), dto.organizerDetails(), dto.ticketPrice());
                });

        //When
        List<EventDto> events = eventMapper.mapToEventList(inputStream, EventDto.class);

        //Then
        assertEquals(1, events.size());
        assertEquals(eventDto.id(), events.getFirst().id());
        assertEquals(eventDto.name(), events.getFirst().name());
        assertEquals(eventDto.dateTime(), events.getFirst().dateTime());
        assertEquals(eventDto.venue(), events.getFirst().venue());
        assertEquals(eventDto.maxCapacity(), events.getFirst().maxCapacity());
        assertEquals(eventDto.organizerDetails(), events.getFirst().organizerDetails());
        assertEquals(eventDto.ticketPrice(), events.getFirst().ticketPrice());
    }

    @Test
    public void testMapToEventList_whenJsonNotValid_ExceptionThrown() throws IOException {
        //Given
        String json = "{ \"events\": [ {\"name\": \"Event 1\" } ] }";
        InputStream inputStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        when(objectMapper.readValue(inputStream, EventListDto.class))
                .thenThrow(new IOException());

        //When, Then
        assertThrows(IOException.class, () -> eventMapper.mapToEventList(inputStream, EventDto.class));
    }

    private static InputStream getInputStream(EventDto dto) {
        String json = "{ \"events\": [ { " +
                "\"id\": \"" + dto.id() + "\", " +
                "\"name\": \"" + dto.name() + "\", " +
                "\"dateTime\": \"" + dto.dateTime() + "\", " +
                "\"venue\": \"" + dto.venue() + "\", " +
                "\"maxCapacity\": " + dto.maxCapacity() + ", " +
                "\"organizerDetails\": \"" + dto.organizerDetails() + "\", " +
                "\"ticketPrice\": " + dto.ticketPrice() + " " +
                "} ] }";
        return new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
    }
}
