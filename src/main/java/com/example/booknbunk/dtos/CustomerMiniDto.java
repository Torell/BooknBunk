package com.example.booknbunk.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerMiniDto {

    private Long id;
    private String name;
   /* private String email;

    public CustomerMiniDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CustomerMiniDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }*/
}
