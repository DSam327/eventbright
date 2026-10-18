package com.workable.event_service.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    private String title;
    private String description;
    private String category;
    private String location;

    private Double price;
    private Integer availableSeats;

    // Constructors
    public Event() {}

    public Event(String title, String description, String category, String location,
                 Double price, Integer availableSeats) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.location = location;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }
}
