package com.workable.event_service.service;

import com.workable.event_service.entity.Event;
import com.workable.event_service.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public Event createEvent(Event event) {
        return repository.save(event);
    }

    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    public List<Event> getByCategory(String category) {
        return repository.findByCategory(category);
    }

    public List<Event> getByLocation(String location) {
        return repository.findByLocation(location);
    }

    public Event getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public Event updateSeats(Long eventId, int seatsToReduce) {

        Event event = repository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getAvailableSeats() < seatsToReduce) {
            throw new RuntimeException("Not enough seats available");
        }

        event.setAvailableSeats(event.getAvailableSeats() - seatsToReduce);

        return repository.save(event);
    }
}