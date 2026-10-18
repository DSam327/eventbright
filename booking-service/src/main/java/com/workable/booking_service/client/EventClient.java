package com.workable.booking_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EventClient {

    private final String EVENT_SERVICE_URL = "http://localhost:8080/events/";

    private final RestTemplate restTemplate = new RestTemplate();

    public Object getEvent(Long eventId) {
        return restTemplate.getForObject(EVENT_SERVICE_URL + eventId, Object.class);
    }

    public void reduceSeats(Long eventId, int seats) {
        restTemplate.put(EVENT_SERVICE_URL + eventId + "/seats?seats=" + seats, null);
    }
}