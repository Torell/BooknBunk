package com.example.booknbunk.repositories;

import com.example.booknbunk.models.Event;
import com.example.booknbunk.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findAllByRoomOrderByTimeStampDesc(Room room);

}
