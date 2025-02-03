package eu.deltasource.event_system.controller;

import eu.deltasource.dto.CreateEventDto;
import eu.deltasource.dto.EventViewDto;
import eu.deltasource.event_system.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public List<EventViewDto> showAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody CreateEventDto createEventDto) {
        String addedEventName = eventService.create(createEventDto);
        return ResponseEntity.ok("Successfully added event: " + addedEventName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        eventService.delete(id);
        return ResponseEntity.ok("The event is successfully deleted!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") UUID id,
                                         @RequestBody CreateEventDto createEventDto) {
        eventService.updateEvent(id, createEventDto);
        return ResponseEntity.ok("The event is successfully updated!");
    }
}
