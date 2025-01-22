package eu.deltasource.event_system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.deltasource.EventMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public EventMapper eventMapper(ObjectMapper objectMapper){
        return new EventMapper(objectMapper);
    }
}
