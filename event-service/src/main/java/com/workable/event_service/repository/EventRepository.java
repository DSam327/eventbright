package com.workable.event_service.repository;

import com.workable.event_service.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCategory(String category);
    List<Event> findByLocation(String location);
}
