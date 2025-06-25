package com.workouttracker.workout_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.workouttracker.workout_tracker.model.Workout;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    // Méthode pour récupérer toutes les séances d'un utilisateur
    List<Workout> findByUserId(Long userId);
}
