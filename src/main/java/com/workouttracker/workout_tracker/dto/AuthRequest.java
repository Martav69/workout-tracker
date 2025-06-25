package com.workouttracker.workout_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Requête de login.
 */
@Data
public class AuthRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
