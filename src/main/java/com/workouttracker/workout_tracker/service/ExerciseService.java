package com.workouttracker.workout_tracker.service;

import com.workouttracker.workout_tracker.dto.ExerciseDTO;
import java.util.List;

/**
 * Interface définissant les opérations CRUD pour les exercices.
 */
public interface ExerciseService {
    ExerciseDTO createExercise(Long workoutId, ExerciseDTO dto);
    List<ExerciseDTO> getAllExercises();
    List<ExerciseDTO> getExercisesByWorkout(Long workoutId);
    ExerciseDTO getExerciseById(Long exerciseId);
    ExerciseDTO updateExercise(Long exerciseId, ExerciseDTO dto);
    void deleteExercise(Long exerciseId);
}