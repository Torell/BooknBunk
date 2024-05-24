package com.example.booknbunk.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordResetRequest {
    private String email;
    private String token;
    private String newPassword;
}
