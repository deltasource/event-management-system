package eu.deltasource.event_system.controller;

import eu.deltasource.event_system.model.Event;
import eu.deltasource.event_system.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Event>> showAllEvents() {
        List<Event> allEvents = eventService.getAllEvents();
        return ResponseEntity.ok(allEvents);
    }
}
