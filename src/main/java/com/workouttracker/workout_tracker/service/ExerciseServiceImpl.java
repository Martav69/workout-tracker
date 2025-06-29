package com.workouttracker.workout_tracker.service;

import com.workouttracker.workout_tracker.dto.ExerciseDTO;
import com.workouttracker.workout_tracker.mapper.ExerciseMapper;
import com.workouttracker.workout_tracker.model.Exercise;
import com.workouttracker.workout_tracker.model.Workout;
import com.workouttracker.workout_tracker.repository.ExerciseRepository;
import com.workouttracker.workout_tracker.repository.WorkoutRepository;
import com.workouttracker.workout_tracker.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseMapper exerciseMapper;

    @Override
    @Transactional
    public ExerciseDTO createExercise(Long workoutId, ExerciseDTO dto) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Séance non trouvée : " + workoutId));
        // Sécurité
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (!workout.getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("Accès refusé à la séance " + workoutId);
        }

        Exercise exercise = exerciseMapper.toEntity(dto);
        exercise.setWorkout(workout);
        return exerciseMapper.toDto(exerciseRepository.save(exercise));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseDTO> getAllExercises() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        // on ne veut que les exercices des séances du user
        return exerciseRepository.findAll().stream()
                .filter(e -> e.getWorkout().getUser().getId().equals(currentUserId))
                .map(exerciseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseDTO> getExercisesByWorkout(Long workoutId) {
        // Vérif séance + appartenance
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Séance non trouvée : " + workoutId));
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (!workout.getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("Accès refusé à la séance " + workoutId);
        }
        return exerciseRepository.findByWorkoutId(workoutId)
                .stream().map(exerciseMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ExerciseDTO getExerciseById(Long exerciseId) {
        Exercise e = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Exercice non trouvé : " + exerciseId));
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (!e.getWorkout().getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("Accès refusé à l'exercice " + exerciseId);
        }
        return exerciseMapper.toDto(e);
    }

    @Override
    @Transactional
    public ExerciseDTO updateExercise(Long exerciseId, ExerciseDTO dto) {
        Exercise existing = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Exercice non trouvé : " + exerciseId));
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (!existing.getWorkout().getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("Accès refusé à l'exercice " + exerciseId);
        }
        existing.setName(dto.getName());
        existing.setSets(dto.getSets());
        existing.setReps(dto.getReps());
        existing.setWeight(dto.getWeight());
        return exerciseMapper.toDto(exerciseRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteExercise(Long exerciseId) {
        Exercise e = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Exercice non trouvé : " + exerciseId));
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (!e.getWorkout().getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("Accès refusé à l'exercice " + exerciseId);
        }
        exerciseRepository.delete(e);
    }
}

