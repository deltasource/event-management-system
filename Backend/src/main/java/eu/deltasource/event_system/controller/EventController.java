package eu.deltasource.event_system.controller;

import eu.deltasource.dto.CreateEventDto;
import eu.deltasource.dto.EventDto;
import eu.deltasource.event_system.model.Attendee;
import eu.deltasource.event_system.service.AttendeeService;
import eu.deltasource.event_system.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final AttendeeService attendeeService;

    public EventController(EventService eventService, AttendeeService attendeeService) {
        this.eventService = eventService;
        this.attendeeService = attendeeService;
    }

    @GetMapping()
    public List<EventDto> showAllEvents() {
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

    @GetMapping("/{id}/attendees")
    public ResponseEntity<List<Attendee>> getAttendees(@PathVariable("id") UUID id) {
        List<Attendee> attendees = attendeeService.getAllByEvent(id);
        return ResponseEntity.ok(attendees);
    }

    @PostMapping("/{eventId}/register/{attendeeId}")
    public ResponseEntity<String> registerAttendee(@PathVariable("eventId") UUID eventId,
                                                   @PathVariable("attendeeId") UUID attendeeId) {
        eventService.addAttendeeToEvent(eventId, attendeeId);
        return ResponseEntity.ok("The attendee is successfully added to the event!");
    }
}
