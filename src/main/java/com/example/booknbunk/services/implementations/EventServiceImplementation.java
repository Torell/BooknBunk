package com.example.booknbunk.services.implementations;

import com.example.booknbunk.repositories.EventRepository;
import com.example.booknbunk.services.interfaces.EventService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImplementation implements EventService {

    EventRepository eventRepository;

    @Override
    @Transactional
    public void



}
