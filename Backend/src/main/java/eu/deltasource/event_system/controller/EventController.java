package eu.deltasource.event_system.controller;

import eu.deltasource.event_system.dto.EventViewDto;
import eu.deltasource.event_system.service.EventService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/getAll")
    public List<EventViewDto> showAllEvents() {
        return eventService.getAllEvents();
    }
}
