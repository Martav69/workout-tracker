package com.workouttracker.workout_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Réponse qui contient le JWT.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
