package com.workouttracker.workout_tracker.controller;

import com.workouttracker.workout_tracker.dto.ExerciseDTO;
import com.workouttracker.workout_tracker.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts/{workoutId}/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    /**
     * Crée un exercice lié à la séance spécifiée.
     */
    @PostMapping
    public ResponseEntity<ExerciseDTO> create(
            @PathVariable Long workoutId,
            @Valid @RequestBody ExerciseDTO dto
    ) {
        dto.setWorkoutId(workoutId);
        ExerciseDTO created = exerciseService.createExercise(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Liste tous les exercices de la séance.
     */
    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> list(
            @PathVariable Long workoutId
    ) {
        List<ExerciseDTO> list = exerciseService.getExercisesByWorkout(workoutId);
        return ResponseEntity.ok(list);
    }

    /**
     * Récupère un exercice spécifique.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDTO> getOne(
            @PathVariable Long workoutId,
            @PathVariable Long id
    ) {
        ExerciseDTO dto = exerciseService.getExerciseById(workoutId, id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Met à jour un exercice existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDTO> update(
            @PathVariable Long workoutId,
            @PathVariable Long id,
            @Valid @RequestBody ExerciseDTO dto
    ) {
        ExerciseDTO updated = exerciseService.updateExercise(workoutId, id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Supprime un exercice.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long workoutId,
            @PathVariable Long id
    ) {
        exerciseService.deleteExercise(workoutId, id);
        return ResponseEntity.noContent().build();
    }
}
