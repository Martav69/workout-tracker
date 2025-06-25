package com.workouttracker.workout_tracker.util;

import com.workouttracker.workout_tracker.model.User;
import com.workouttracker.workout_tracker.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    private static UserRepository userRepository;

    public SecurityUtils(UserRepository userRepo) {
        SecurityUtils.userRepository = userRepo;
    }

    /** Récupère l’ID de l’utilisateur actuellement authentifié */
    public static Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // correspond à user.getUsername()
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Utilisateur authentifié introuvable"));
        return user.getId();
    }
}
