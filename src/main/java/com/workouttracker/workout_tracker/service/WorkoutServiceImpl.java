package com.workouttracker.workout_tracker.service;

import com.workouttracker.workout_tracker.dto.WorkoutDTO;
import com.workouttracker.workout_tracker.mapper.WorkoutMapper;
import com.workouttracker.workout_tracker.model.User;
import com.workouttracker.workout_tracker.model.Workout;
import com.workouttracker.workout_tracker.repository.UserRepository;
import com.workouttracker.workout_tracker.repository.WorkoutRepository;
import com.workouttracker.workout_tracker.util.SecurityUtils;
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
        // 1) Récupérer l’utilisateur connecté
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé : " + currentUserId));

        // 2) Convertir DTO → entité et lier l’utilisateur
        Workout workout = workoutMapper.toEntity(dto);
        workout.setUser(user);

        // 3) Lier chaque exercice à la séance
        if (workout.getExercises() != null) {
            workout.getExercises().forEach(ex -> ex.setWorkout(workout));
        }

        // 4) Sauvegarder et renvoyer le DTO
        Workout saved = workoutRepository.save(workout);
        return workoutMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutDTO> getWorkoutsByUser() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return workoutRepository.findByUserId(currentUserId).stream()
                .map(workoutMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutDTO getWorkoutById(Long workoutId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Séance non trouvée : " + workoutId));
        if (!workout.getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("Accès refusé à la séance " + workoutId);
        }
        return workoutMapper.toDto(workout);
    }

    @Override
    @Transactional
    public WorkoutDTO updateWorkout(Long workoutId, WorkoutDTO dto) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Workout existing = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Séance non trouvée : " + workoutId));
        if (!existing.getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("Accès refusé à la séance " + workoutId);
        }

        // Mettre à jour les champs simples
        existing.setName(dto.getName());
        existing.setDate(dto.getDate());
        existing.setDurationMinutes(dto.getDurationMinutes());
        existing.setNotes(dto.getNotes());

        // Remplacer les exercices
        existing.getExercises().clear();
        if (dto.getExercises() != null) {
            workoutMapper.toEntity(dto).getExercises().forEach(ex -> {
                ex.setWorkout(existing);
                existing.getExercises().add(ex);
            });
        }

        Workout saved = workoutRepository.save(existing);
        return workoutMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteWorkout(Long workoutId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Séance non trouvée : " + workoutId));
        if (!workout.getUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("Accès refusé à la séance " + workoutId);
        }
        workoutRepository.delete(workout);
    }
}
