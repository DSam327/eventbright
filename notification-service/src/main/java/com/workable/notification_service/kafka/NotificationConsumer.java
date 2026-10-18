package com.workable.notification_service.kafka;

import com.workable.notification_service.dto.PaymentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @KafkaListener(topics = "payment-events", groupId = "notification-group")
    public void consume(PaymentEvent event) {

        System.out.println("🔔 Notification: Booking "
                + event.getBookingId()
                + " payment status: "
                + event.getStatus());
    }
}