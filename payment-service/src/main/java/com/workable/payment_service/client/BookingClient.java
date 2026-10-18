package com.workable.payment_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BookingClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String BASE_URL = "http://localhost:8080/bookings/";

    public void updateBookingStatus(Long bookingId, String status) {
        restTemplate.put(BASE_URL + bookingId + "/status?status=" + status, null);
    }
}
