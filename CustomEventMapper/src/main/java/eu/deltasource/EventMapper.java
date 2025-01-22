package eu.deltasource;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.deltasource.dto.EventListDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class EventMapper {
    private final ObjectMapper objectMapper;

    public EventMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> List<T> mapToEventList(InputStream inputStream, Class<T> targetClass) throws IOException {
        EventListDto eventListDto = objectMapper.readValue(inputStream, EventListDto.class);
        return eventListDto.events().stream()
                .map(eventDto -> objectMapper.convertValue(eventDto, targetClass))
                .toList();
    }

}
