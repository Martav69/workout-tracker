package com.workouttracker.workout_tracker.service;

import com.workouttracker.workout_tracker.dto.ExerciseDTO;
import java.util.List;

/**
 * Interface définissant les opérations CRUD pour les exercices.
 */
public interface ExerciseService {
    ExerciseDTO createExercise(ExerciseDTO dto);
    List<ExerciseDTO> getExercisesByWorkout(Long workoutId);
    ExerciseDTO getExerciseById(Long workoutId, Long exerciseId);
    ExerciseDTO updateExercise(Long workoutId, Long exerciseId, ExerciseDTO dto);
    void deleteExercise(Long workoutId, Long exerciseId);
}