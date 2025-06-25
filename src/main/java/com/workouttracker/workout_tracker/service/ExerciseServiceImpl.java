package com.workouttracker.workout_tracker.service;

import com.workouttracker.workout_tracker.dto.ExerciseDTO;
import com.workouttracker.workout_tracker.mapper.ExerciseMapper;
import com.workouttracker.workout_tracker.model.Exercise;
import com.workouttracker.workout_tracker.model.Workout;
import com.workouttracker.workout_tracker.repository.ExerciseRepository;
import com.workouttracker.workout_tracker.repository.WorkoutRepository;
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
    public ExerciseDTO createExercise(ExerciseDTO dto) {
        Workout workout = workoutRepository.findById(dto.getWorkoutId())
                .orElseThrow(() -> new IllegalArgumentException("Séance non trouvée : " + dto.getWorkoutId()));

        Exercise exercise = exerciseMapper.toEntity(dto);
        exercise.setWorkout(workout);

        Exercise saved = exerciseRepository.save(exercise);
        return exerciseMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseDTO> getExercisesByWorkout(Long workoutId) {
        return exerciseRepository.findByWorkoutId(workoutId).stream()
                .map(exerciseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ExerciseDTO getExerciseById(Long workoutId, Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Exercice non trouvé : " + exerciseId));
        if (!exercise.getWorkout().getId().equals(workoutId)) {
            throw new IllegalArgumentException("Accès refusé à l'exercice " + exerciseId);
        }
        return exerciseMapper.toDto(exercise);
    }

    @Override
    @Transactional
    public ExerciseDTO updateExercise(Long workoutId, Long exerciseId, ExerciseDTO dto) {
        Exercise existing = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Exercice non trouvé : " + exerciseId));
        if (!existing.getWorkout().getId().equals(workoutId)) {
            throw new IllegalArgumentException("Accès refusé à l'exercice " + exerciseId);
        }

        existing.setName(dto.getName());
        existing.setSets(dto.getSets());
        existing.setReps(dto.getReps());
        existing.setWeight(dto.getWeight());

        Exercise saved = exerciseRepository.save(existing);
        return exerciseMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteExercise(Long workoutId, Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Exercice non trouvé : " + exerciseId));
        if (!exercise.getWorkout().getId().equals(workoutId)) {
            throw new IllegalArgumentException("Accès refusé à l'exercice " + exerciseId);
        }
        exerciseRepository.delete(exercise);
    }
}
