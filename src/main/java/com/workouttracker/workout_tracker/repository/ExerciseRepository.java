package com.workouttracker.workout_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.workouttracker.workout_tracker.model.Exercise;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    // Récupère tous les exercices d'une séance donnée
    List<Exercise> findByWorkoutId(Long workoutId);
}
