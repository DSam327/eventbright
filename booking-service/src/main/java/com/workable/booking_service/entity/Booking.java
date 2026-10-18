package com.workable.booking_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // from JWT later

    @Setter
    @Getter
    private Long eventId;

    @Getter
    private int seatsBooked;

    @Setter
    private String status; // PENDING, CONFIRMED

    @Setter
    private LocalDateTime bookingTime;

    public Booking() {}

    // getters & setters


}
