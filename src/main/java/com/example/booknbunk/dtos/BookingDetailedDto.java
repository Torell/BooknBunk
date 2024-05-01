package com.example.booknbunk.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDetailedDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private CustomerMiniDto customerMiniDto;
    private RoomMiniDto roomMiniDto;
    private int extraBed;


}
