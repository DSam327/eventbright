package com.workable.payment_service.kafka;

import com.workable.payment_service.dto.BookingEvent;
import com.workable.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "booking-events", groupId = "payment-group")
    public void consume(BookingEvent event) {
        paymentService.processFromEvent(event);
    }
}
