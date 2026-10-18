package com.workable.booking_service.kafka;

import com.workable.booking_service.dto.BookingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookingProducer {

    @Autowired
    private KafkaTemplate<String, BookingEvent> kafkaTemplate;

    private static final String TOPIC = "booking-events";

    public void sendBookingEvent(BookingEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
