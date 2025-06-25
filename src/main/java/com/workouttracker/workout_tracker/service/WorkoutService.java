package com.workouttracker.workout_tracker.service;

import com.workouttracker.workout_tracker.dto.WorkoutDTO;
import java.util.List;

public interface WorkoutService {
    WorkoutDTO createWorkout(WorkoutDTO dto);


    List<WorkoutDTO> getWorkoutsByUser();


    WorkoutDTO getWorkoutById(Long workoutId);


    WorkoutDTO updateWorkout(Long workoutId, WorkoutDTO dto);


    void deleteWorkout(Long workoutId);
}
