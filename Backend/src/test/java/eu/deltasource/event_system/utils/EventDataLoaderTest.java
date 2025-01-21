package eu.deltasource.event_system.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.deltasource.event_system.dto.EventListDto;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventDataLoaderTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private EventDataLoader inMemoryDatabaseInitializer;

    @Test
    public void testInitInMemoryEvents_whenFileIsFound_savesToRepository() throws IOException {
        //Given
        Event event = new Event(UUID.randomUUID(), "Event", null, "Venue", 100, "Organizer", 20.0);
        EventListDto wrapper = new EventListDto(List.of(event));
        when(objectMapper.readValue(any(File.class), eq(EventListDto.class)))
                .thenReturn(wrapper);

        //When
        inMemoryDatabaseInitializer.loadEvents();

        //Then
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void testInitInMemoryEvents_whenFileNotFound_throwsException() throws IOException {
        //Given
        Event event = new Event(UUID.randomUUID(), "Event", null, "Venue", 100, "Organizer", 20.0);
        EventListDto wrapper = new EventListDto(List.of(event));
        when(objectMapper.readValue(any(File.class), eq(EventListDto.class)))
                .thenThrow(new IOException());

        //When
        assertThrows(IOException.class, () -> {
            inMemoryDatabaseInitializer.loadEvents();
        });

        //Then
        verify(eventRepository, times(0)).save(event);
    }
}
