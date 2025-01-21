package eu.deltasource.event_system.dto;

import eu.deltasource.event_system.model.Event;

import java.util.List;

/**
 * A helper class that wraps the fetched data from a .json file
 */
public record EventListDto(List<Event> events) {
}
