package com.example.booknbunk.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
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

    public List<BookingMiniDto> getBookingMiniDtoList() {
        if (this.bookingMiniDtoList == null) {
            return Collections.emptyList();
        }
        return this.bookingMiniDtoList;
    }


}
