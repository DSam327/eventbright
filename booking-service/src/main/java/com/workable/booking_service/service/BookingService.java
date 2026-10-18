package com.workable.booking_service.service;

import com.workable.booking_service.dto.BookingEvent;
import com.workable.booking_service.entity.Booking;
import com.workable.booking_service.kafka.BookingProducer;
import com.workable.booking_service.repository.BookingRepository;
import com.workable.booking_service.client.EventClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingProducer producer;

    @Autowired
    private BookingRepository repository;

    @Autowired
    private EventClient eventClient;

    public Booking createBooking(Booking booking) {

        // 1. Fetch event
        Object event = eventClient.getEvent(booking.getEventId());

        if (event == null) {
            throw new RuntimeException("Event not found");
        }

        // 2. Reduce seats (this will throw error if not enough)
        eventClient.reduceSeats(booking.getEventId(), booking.getSeatsBooked());

        // 3. Save booking
        booking.setStatus("CONFIRMED");
        booking.setBookingTime(LocalDateTime.now());

        Booking saved = repository.save(booking);

        // Create event
        BookingEvent Kafkaevent = new BookingEvent();
        Kafkaevent.setBookingId(saved.getEventId());
        Kafkaevent.setAmount(booking.getSeatsBooked() * 500.0); // example

        // Send to Kafka
        producer.sendBookingEvent(Kafkaevent);

        return saved;
    }

    public void updateStatus(Long id, String status) {
        Booking booking = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(status);
        repository.save(booking);
    }

    public List<Booking> getBookingsByUser(String username) {
        return repository.findByUsername(username);
    }
}
