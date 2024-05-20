package com.example.booknbunk.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blacklist {

    private String name;
    private String email;
    private boolean Ok;

}
