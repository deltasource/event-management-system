# List of criteria which should be met

## 1. Mapping logic should be handled in the Custom Mapper module only
All the mapping between objects should be done in the custom mapper module.
This approach ensures consistency and reusability across the application.

Example: Fetching events entities from repository and mapping them to dto objects,
using the customer mapper module.
```java
public List<EventViewDto> getAllEvents() {
    return eventRepository.getAll().stream()
            .map(e -> eventMapper.mapFromTo(e, EventViewDto.class))
            .toList();
}
```
## 2. Test structure must follow "//Given, //When, //Then" convention
All unit and integration tests must follow the Given-When-Then format to clearly define the setup, execution, and expected outcome.
This structure improves test readability, making it easier to understand.

Example: The Given-When-Then format in a unit test for saving an event.
```java
 @Test
public void saveEvent() {
    //Given
    Event event = new Event(UUID.randomUUID(), "Event", LocalDateTime.now(), "venue", 100, "details", 10);

    //When
    eventRepository.save(event);

    //Then
    assertEquals(1, eventRepository.getAll().size());
}
```

## 3. Javadoc is required for all service and helper classes outside the MVC pattern
Any class acting as a service or helper, which does not fall within the traditional Model-View-Controller (MVC) design pattern,
must include Javadoc documentation. The documentation should clearly explain the purpose and functionality of the class within the application's architecture.
This will ensure that future developers understand the role of these classes and their intended use within the system.

Example: Javadoc for the helper class which loads the in-memory
database when the project is started.
```java
/**
 * Class which is responsible for loading events, fetched
 * from a .json file, in the in-memory database
 */
@Component
public class EventDataLoader
```
## 4. Data transfer between controller and service should only be done using DTOs
Data should never be passed directly between the controller and service using entities.
This communication (from controller to service, and vice versa) must be handled through Data Transfer Objects (DTOs).
This ensures that only the necessary data is passed between layers.

Example: The service layer is fetching data from the repository,
maps it to dto objects and pass it to the controller.
```java
// Service method
public List<EventViewDto> getAllEvents() {
        return eventRepository.getAll().stream()
                .map(e -> eventMapper.mapFromTo(e, EventViewDto.class))
                .toList();
}
// Controller method
@GetMapping("/getAll")
public List<EventViewDto> showAllEvents() {
    return eventService.getAllEvents();
}
```


