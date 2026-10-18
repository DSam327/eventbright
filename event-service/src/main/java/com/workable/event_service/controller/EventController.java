package com.workable.event_service.controller;

import com.workable.event_service.entity.Event;
import com.workable.event_service.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService service;

    // CREATE EVENT
    @PostMapping
    public Event create(@RequestBody Event event) {
        return service.createEvent(event);
    }

    // GET ALL EVENTS
    @GetMapping
    public List<Event> getAll() {
        return service.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // FILTER BY CATEGORY
    @GetMapping("/category/{category}")
    public List<Event> getByCategory(@PathVariable String category) {
        return service.getByCategory(category);
    }

    // FILTER BY LOCATION
    @GetMapping("/location/{location}")
    public List<Event> getByLocation(@PathVariable String location) {
        return service.getByLocation(location);
    }

    @PutMapping("/{id}/seats")
    public Event updateSeats(@PathVariable Long id, @RequestParam int seats) {
        return service.updateSeats(id, seats);
    }
}
