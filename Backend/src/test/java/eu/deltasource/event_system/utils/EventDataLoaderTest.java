package eu.deltasource.event_system.utils;

import eu.deltasource.EventMapper;
import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
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
    private EventMapper eventMapper;
    @InjectMocks
    private EventDataLoader inMemoryDatabaseInitializer;

    @Test
    public void testInitInMemoryEvents_whenFileIsFound_savesToRepository() throws IOException {
        //Given
        Event event = new Event(UUID.randomUUID(), "Event", null, "Venue", 100, "Organizer", 20.0);
        List<Event> events = List.of(event);
        when(eventMapper.mapToEventList(any(InputStream.class), eq(Event.class)))
                .thenReturn(events);

        //When
        inMemoryDatabaseInitializer.loadEvents();

        //Then
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void testInitInMemoryEvents_whenFileNotFound_throwsException() throws IOException {
        //Given
        when(eventMapper.mapToEventList(any(InputStream.class), eq(Event.class)))
                .thenThrow(new IOException());

        //When, Then
        assertThrows(IOException.class, () -> {
            inMemoryDatabaseInitializer.loadEvents();
        });
    }
}
