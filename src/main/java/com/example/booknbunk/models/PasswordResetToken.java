package com.example.booknbunk.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Token must be a valid UUID")
    @NotNull
    private String token;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    @NotNull
    @FutureOrPresent(message = "expiration date should be present or future")
    private LocalDateTime expirationDateTime;

    public PasswordResetToken(String token, User user, LocalDateTime expirationDateTime) {
        this.token = token;
        this.user = user;
        this.expirationDateTime = expirationDateTime;
    }
}
