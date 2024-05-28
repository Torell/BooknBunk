package com.example.booknbunk.repositories;

import com.example.booknbunk.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByRoomId(Long id);
}
