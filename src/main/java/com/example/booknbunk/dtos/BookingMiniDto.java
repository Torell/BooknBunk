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
public class BookingMiniDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
}
