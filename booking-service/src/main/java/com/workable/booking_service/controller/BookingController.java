package com.workable.booking_service.controller;

import com.workable.booking_service.entity.Booking;
import com.workable.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService service;

    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        return service.createBooking(booking);
    }

    @GetMapping("/{username}")
    public List<Booking> getUserBookings(@PathVariable String username) {
        return service.getBookingsByUser(username);
    }

    @PutMapping("/{id}/status")
    public void updateStatus(@PathVariable Long id, @RequestParam String status) {
        service.updateStatus(id, status);
    }
}
