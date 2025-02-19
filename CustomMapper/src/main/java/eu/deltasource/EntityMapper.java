package eu.deltasource;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.deltasource.dto.EventListDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * This class is responsible for mapping events between different formats
 * It uses Jackson's ObjectMapper to perform the deserialization and mapping.
 */
public class EntityMapper {
    private final ObjectMapper objectMapper;

    public EntityMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * This method maps a JSON input stream to target objects.
     *
     * @param inputStream the input stream containing JSON data.
     * @param targetClass The type of the objects that will be in the returned list.
     */
    public <T> List<T> mapToEventList(InputStream inputStream, Class<T> targetClass) throws IOException {
        EventListDto eventListDto = objectMapper.readValue(inputStream, EventListDto.class);
        return eventListDto.events().stream()
                .map(eventDto -> objectMapper.convertValue(eventDto, targetClass))
                .toList();
    }

    public <T, G> G mapFromTo(T source, Class<G> targetClass) {
        return objectMapper.convertValue(source, targetClass);
    }
}
