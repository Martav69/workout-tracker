package com.workouttracker.workout_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Requête d’inscription.
 */
@Data
public class RegisterRequest {
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, message = "Le mot de passe doit faire au moins 6 caractères")
    private String password;
}
