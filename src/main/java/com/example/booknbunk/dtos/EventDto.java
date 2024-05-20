package com.example.booknbunk.dtos;

import com.example.booknbunk.models.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EventDto {

    private Long id;
    private Long roomNo;
    private LocalDateTime timeStamp;
    private String eventType;
    private String cleaningByUser;

}
