package com.example.booknbunk.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    private String eventText;
    private LocalDateTime timeStamp;

    @JoinColumn(name = "room_id")
    @NonNull
    @ManyToOne
    private Room room;


}
