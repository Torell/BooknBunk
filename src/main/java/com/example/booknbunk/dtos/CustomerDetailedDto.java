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
public class CustomerDetailedDto {

    private Long id;
    private String name;
    private String email;
    private List<BookingMiniDto> bookingMiniDtoList;

}
