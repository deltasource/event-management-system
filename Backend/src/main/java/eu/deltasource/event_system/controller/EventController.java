package eu.deltasource.event_system.controller;

import eu.deltasource.event_system.dto.AddEventDto;
import eu.deltasource.event_system.dto.EventViewDto;
import eu.deltasource.event_system.exceptions.EventNotFoundException;
import eu.deltasource.event_system.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<String> addEvent(@Valid @RequestBody AddEventDto eventDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getAllErrors()
                    .forEach(error -> errorMessages.append(error.getDefaultMessage()).append(" "));
            return ResponseEntity.badRequest().body(errorMessages.toString());
        }
        String addedEventName = eventService.add(eventDto);
        return ResponseEntity.ok("Successfully added event: " + addedEventName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") UUID id) {
        eventService.delete(id);
        return ResponseEntity.ok("The event is successfully deleted!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEvent(@PathVariable("id") UUID id,
                                              @Valid @RequestBody AddEventDto addEventDto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getAllErrors()
                    .forEach(error -> errorMessages.append(error.getDefaultMessage()).append(" "));
            return ResponseEntity.badRequest().body(errorMessages.toString());
        }
        eventService.updateEvent(id, addEventDto);
        return ResponseEntity.ok("The event is successfully updated!");
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<String> handleEventNotFound(EventNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
