package eu.deltasource.event_adapter_service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class AdapterController {
    private final AdapterService adapterService;

    public AdapterController(AdapterService adapterService) {
        this.adapterService = adapterService;
    }

    @GetMapping("fetch-events")
    public ResponseEntity<List<CreateEventDto>> fetchEvents() throws Exception {
        String eventbriteApiUrl = "https://www.eventbriteapi.com/v3/venues/1/events/?token=PHEFETJ2BKXO36JX5YAD";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(eventbriteApiUrl,
                HttpMethod.GET,
                null,
                String.class);
        List<CreateEventDto> fetchedEvents = adapterService.handleResponse(response.getBody());
        return ResponseEntity.ok().body(fetchedEvents);
    }
}
