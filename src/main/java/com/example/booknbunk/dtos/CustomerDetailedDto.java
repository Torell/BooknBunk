package com.example.booknbunk.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDetailedDto {

    private Long id;
    @NotEmpty(message = "Please enter a name")
    private String name;

    @NotEmpty(message = "Please enter an email address")
    @Email(message = "Please enter a valid email address")
    private String email;

    private List<BookingMiniDto> bookingMiniDtoList;

    public List<BookingMiniDto> getBookingMiniDtoList() {
        if (this.bookingMiniDtoList == null) {
            return Collections.emptyList();
        }
        return this.bookingMiniDtoList;
    }


}
