package com.workable.payment_service.kafka;

import com.workable.payment_service.dto.PaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentProducer {

    @Autowired
    private KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    private static final String TOPIC = "payment-events";

    public void sendPaymentEvent(PaymentEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
