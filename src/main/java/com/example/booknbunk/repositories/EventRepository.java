package com.example.booknbunk.repositories;

import com.example.booknbunk.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
