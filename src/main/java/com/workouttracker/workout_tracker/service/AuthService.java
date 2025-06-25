package com.workouttracker.workout_tracker.service;

import com.workouttracker.workout_tracker.dto.*;

public interface AuthService {
    /**
     * Crée un nouvel utilisateur (rôle USER par défaut).
     */
    UserDTO register(RegisterRequest request);

    /**
     * Authentifie et renvoie un JWT.
     */
    AuthResponse authenticate(AuthRequest request);
}
