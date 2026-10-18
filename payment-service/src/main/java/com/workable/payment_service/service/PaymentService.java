package com.workable.payment_service.service;

import com.workable.payment_service.dto.BookingEvent;
import com.workable.payment_service.dto.PaymentEvent;
import com.workable.payment_service.entity.Payment;
import com.workable.payment_service.kafka.PaymentProducer;
import com.workable.payment_service.repository.PaymentRepository;
import com.workable.payment_service.client.BookingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private BookingClient bookingClient;

    @Autowired
    private PaymentProducer producer;

    public Payment processPayment(Payment payment) {

        // Mock payment logic
        boolean success = new Random().nextBoolean();

        if (success) {
            payment.setStatus("SUCCESS");
            bookingClient.updateBookingStatus(payment.getBookingId(), "CONFIRMED");
        } else {
            payment.setStatus("FAILED");
            bookingClient.updateBookingStatus(payment.getBookingId(), "FAILED");
        }

        payment.setPaymentTime(LocalDateTime.now());

        Payment saved = repository.save(payment);

        // send event
        PaymentEvent event = new PaymentEvent();
        event.setBookingId(payment.getBookingId());
        event.setStatus(payment.getStatus());

        producer.sendPaymentEvent(event);

        System.out.println("Payment done and sent");

        return saved;
    }

    public void processFromEvent(BookingEvent event) {

        Payment payment = new Payment();
        payment.setBookingId(event.getBookingId());
        payment.setAmount(event.getAmount());
        processPayment(payment);
    }
}
