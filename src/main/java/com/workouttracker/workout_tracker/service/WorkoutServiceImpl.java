package com.workouttracker.workout_tracker.service;

import com.workouttracker.workout_tracker.dto.WorkoutDTO;
import com.workouttracker.workout_tracker.mapper.WorkoutMapper;
import com.workouttracker.workout_tracker.model.User;
import com.workouttracker.workout_tracker.model.Workout;
import com.workouttracker.workout_tracker.repository.UserRepository;
import com.workouttracker.workout_tracker.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final WorkoutMapper workoutMapper;

    @Override
    @Transactional
    public WorkoutDTO createWorkout(WorkoutDTO dto) {
        // 1) Charger l’utilisateur
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé : " + dto.getUserId()));

        // 2) Convertir DTO → entité
        Workout workout = workoutMapper.toEntity(dto);
        workout.setUser(user);

        // 3) Lier chaque exercice à la séance
        if (workout.getExercises() != null) {
            workout.getExercises()
                    .forEach(ex -> ex.setWorkout(workout));
        }

        // 4) Sauvegarder
        Workout saved = workoutRepository.save(workout);

        // 5) Convertir entité → DTO et renvoyer
        return workoutMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutDTO> getWorkoutsByUser(Long userId) {
        return workoutRepository.findByUserId(userId).stream()
                .map(workoutMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutDTO getWorkoutById(Long userId, Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Séance non trouvée : " + workoutId));
        if (!workout.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Accès refusé à la séance " + workoutId);
        }
        return workoutMapper.toDto(workout);
    }

    @Override
    @Transactional
    public WorkoutDTO updateWorkout(Long userId, Long workoutId, WorkoutDTO dto) {
        Workout existing = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Séance non trouvée : " + workoutId));
        if (!existing.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Accès refusé à la séance " + workoutId);
        }

        // 1) Mettre à jour les champs simples
        existing.setDate(dto.getDate());
        existing.setDurationMinutes(dto.getDurationMinutes());
        existing.setNotes(dto.getNotes());

        // 2) Remplacer les exercices
        existing.getExercises().clear();
        if (dto.getExercises() != null) {
            workoutMapper.toEntity(dto)
                    .getExercises()
                    .forEach(ex -> {
                        ex.setWorkout(existing);
                        existing.getExercises().add(ex);
                    });
        }

        // 3) Sauvegarder
        Workout saved = workoutRepository.save(existing);
        return workoutMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteWorkout(Long userId, Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Séance non trouvée : " + workoutId));
        if (!workout.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Accès refusé à la séance " + workoutId);
        }
        workoutRepository.delete(workout);
    }
}
