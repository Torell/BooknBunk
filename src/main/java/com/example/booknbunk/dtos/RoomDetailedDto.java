package com.example.booknbunk.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDetailedDto {

    private Long id;
    private int RoomSize;
    private List<BookingMiniDto> bookingMiniDtoList;
}
