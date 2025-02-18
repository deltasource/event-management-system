package eu.deltasource.event_system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.deltasource.EntityMapper;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@Configuration
public class Config {
    @Bean
    public EntityMapper eventMapper(ObjectMapper objectMapper) {
        return new EntityMapper(objectMapper);
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
