package com.example.booknbunk.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {
    private Long id;
    private LocalDateTime timeStamp;
    private Long roomNo;
    private String type;
    private String cleaningByUser;
    private String cleaningStatus;
    private String doorEventType;
}